import {Component, OnInit} from '@angular/core';
import {NgModel} from "@angular/forms";
import {HttpClient} from '@angular/common/http';

declare var $:any;

export class Kernel {
  constructor(
      public kernelSize: number,
      public crossSectionalArea: number,
      public elasticModulus: number,
      public allowableStress: number,
      public concentratedLoad: number,
      public linearVoltage: number) {
  }
}

declare interface TableData {
  name: string;
  headerRow: string[];
  kernels: Kernel[];
  leftSupport: boolean;
  rightSupport: boolean;
}
@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css']
})
export class MapsComponent implements OnInit {
  public tableData: TableData;
  public history: Array<string> = [];
  public kernel: Kernel = new Kernel(0, 0, 0, 0, null, null);
  constructor( private http: HttpClient) { }

  ngOnInit() {
    this.updateHistory();
    this.tableData = {
      name: "",
      headerRow: [ '№', 'Длина стержня', 'Площадь поперечного сечения', 'Модель упругости', 'Допусткаемое напряжение', 'Сосредоточенная нагрузка', 'Погонное напряжение'],
      kernels: [],
      leftSupport: false,
      rightSupport: false
    };
  }

  addKernel(size: NgModel, crossSectionalArea: NgModel, elasticModulus: NgModel, allowableStress: NgModel, concentratedLoad: NgModel, linearVoltage: NgModel) {
    if (size.viewModel != null && crossSectionalArea.viewModel != null && elasticModulus.viewModel != null && allowableStress.viewModel != null) {
      this.tableData.kernels.push(new Kernel(size.viewModel, crossSectionalArea.viewModel,
          elasticModulus.viewModel,
          allowableStress.viewModel,
          concentratedLoad.viewModel != null? concentratedLoad.viewModel: null,
          linearVoltage.viewModel != null? linearVoltage.viewModel : null)
      )
      this.showNotification('success', "Cтержень успешно добавлен");
    } else {
      this.showNotification('danger', "Не верно заполнены <b>обязательные поля</b>.");
    }
  }

  showNotification(color: string, message: string){
    const type = ['','info','success','warning','danger'];

    $.notify({
      icon: "pe-7s-gift",
      message: message
    },{
      type: color,
      timer: 1000,
      placement: {
        from: "top",
        align: 'center'
      }
    });
  }

  setLeftSupport() {
    this.tableData.leftSupport = !this.tableData.leftSupport;
  }

  setRightSupport() {
    this.tableData.rightSupport = !this.tableData.rightSupport;
  }

  insertConstruction(name: NgModel) {
    this.tableData.name = name.viewModel;
    if (this.tableData.name != "" && this.tableData.kernels.length != null) {
      this.http.post<any>("/constructions/", this.tableData).pipe().subscribe((data) => {
        this.updateHistory();
      });
    }
    if (this.tableData.name == "") {
      this.showNotification('danger', "Введите название конструкции");
    }
    if (this.tableData.kernels.length == 0) {
      this.showNotification('danger', "В конструкции отсутствуют стержни")
    }
  }

  getLastConstruction() {
    this.http.get<any>("/constructions/").pipe().subscribe((data) => {
      console.log(data[data.length - 1]);
      this.tableData.kernels = data[data.length - 1].kernels;
      this.tableData.leftSupport = data[data.length - 1].leftSupport;
      this.tableData.rightSupport = data[data.length - 1].rightSupport;
    });
    this.updateHistory();
  }

  reset(name: NgModel) {
    this.tableData.kernels = [];
    this.tableData.name = "";
    this.tableData.leftSupport = false;
    this.tableData.rightSupport = false;
    name.reset();
    this.updateHistory();
  }

  resetKernel(size: NgModel, crossSectionalArea: NgModel, elasticModulus: NgModel, allowableStress: NgModel, concentratedLoad: NgModel, linearVoltage: NgModel) {
    size.reset();
    crossSectionalArea.reset();
    elasticModulus.reset();
    allowableStress.reset();
    concentratedLoad.reset();
    linearVoltage.reset();

  }

  viewHistoryItem(index, name: NgModel) {
    this.http.get<any>("/constructions/").pipe().subscribe((data) => {
          this.tableData.name = data[index].name;
          this.tableData.kernels = data[index].kernels;
        }
    );
  }

  deleteItemByName(name) {
    this.http.put<any>("/constructions/", name).pipe().subscribe((data) => {
      this.updateHistory();
    });
  }

  updateHistory() {
    this.http.get<any>("/constructions/").pipe().subscribe((data) => {
          this.history = [];
          data.forEach(e => {
            this.history.push(e.name);
          })
        }
    );

  }
}
