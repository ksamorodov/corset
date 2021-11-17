import {Component, OnInit} from '@angular/core';
import {NgModel} from "@angular/forms";
import { HttpClient, HttpHeaders, HttpClientModule } from '@angular/common/http';
import {FormsModule} from "@angular/forms";
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
  public kernel: Kernel = new Kernel(0, 0, 0, 0, null, null);
  constructor( private http: HttpClient) { }

  ngOnInit() {
    this.tableData = {
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
      this.showNotification('danger', "Не заполнены <b>обязательные поля</b>.");
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

  insertConstruction() {
    if (this.tableData.kernels.length != null) {
      this.http.post<any>("/constructions/", this.tableData).pipe().subscribe();
    }
  }
}
