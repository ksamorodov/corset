import {Component, OnInit} from '@angular/core';
import {NgModel} from "@angular/forms";
import {FormsModule} from "@angular/forms";
declare var $:any;

export class Kernel {
  constructor(
      public size: number,
      public crossSectionalArea: number,
      public elasticModulus: number,
      public allowableStress: number,
      public concentratedLoad: number,
      public linearVoltage: number) {
  }
}

declare interface TableData {
  headerRow: string[];
  dataRows: Kernel[];
  leftSupport: boolean;
  rightSupport: boolean;
}
@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css']
})
export class MapsComponent implements OnInit {
  public tableData1: TableData;
  public tableData2: TableData;
  public kernel: Kernel = new Kernel(0, 0, 0, 0, null, null,)
  constructor() { }

  ngOnInit() {
    this.tableData1 = {
      headerRow: [ '№', 'Длина стержня', 'Площадь поперечного сечения', 'Модель упругости', 'Допусткаемое напряжение', 'Сосредоточенная нагрузка', 'Погонное напряжение'],
      dataRows: [],
      leftSupport: false,
      rightSupport: false
    };
  }

  addKernel(size: NgModel, crossSectionalArea: NgModel, elasticModulus: NgModel, allowableStress: NgModel, concentratedLoad: NgModel, linearVoltage: NgModel) {
    if (size.viewModel != null && crossSectionalArea.viewModel != null && elasticModulus.viewModel != null && allowableStress.viewModel != null) {
      this.tableData1.dataRows.push(new Kernel(size.viewModel, crossSectionalArea.viewModel,
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
    this.tableData1.leftSupport = !this.tableData1.leftSupport;
  }

  setRightSupport() {
    this.tableData1.rightSupport = !this.tableData1.rightSupport;

  }
}
