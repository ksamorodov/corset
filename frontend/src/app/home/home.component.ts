import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Kernel} from "../maps/maps.component";

declare interface TableData {
    id: number;
    name: string;
    headerRow: string[];
    kernels: Kernel[];
    leftSupport: boolean;
    rightSupport: boolean;
}
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    public history: Array<string> = [];
    constructor( private http: HttpClient) { }
    public tableData: TableData;

    ngOnInit(): void {
        this.tableData = {
            id: null,
            name: "",
            headerRow: [ '№', 'Длина стержня', 'Площадь поперечного сечения', 'Модуль упругости', 'Допусткаемое напряжение', 'Сосредоточенная нагрузка', 'Погонное напряжение'],
            kernels: [],
            leftSupport: false,
            rightSupport: false
        };
        this.updateHistory();
    }

    viewHistoryItem(index) {
        this.http.get<any>("/constructions/").pipe().subscribe((data) => {
            console.log(data[index].id);
                this.tableData.id = data[index].id;
                this.tableData.name = data[index].name;
                this.tableData.kernels = data[index].kernels;
                this.tableData.leftSupport = data[index].leftSupport;
                this.tableData.rightSupport = data[index].rightSupport;
            }
        );
    }

    deleteItemByName(name) {
        this.http.put<any>("/constructions/", name).pipe().subscribe((data) => {
            this.updateHistory();
        });
    }

    calculate(id) {
        this.http.get<any>("/constructions/calculate/" + id).pipe().subscribe((data) => {

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
