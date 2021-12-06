import {Component, OnInit} from '@angular/core';
import {NgModel} from "@angular/forms";
import {HttpClient} from '@angular/common/http';

declare var $: any;

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
    public showVisualisation = false;
    private ctx: CanvasRenderingContext2D;
    private canvas: HTMLCanvasElement;
    public tableData: TableData;
    public history: Array<string> = [];
    public index = "index.jpg";
    public kernel: Kernel = new Kernel(null, null, null, null, null, null);

    constructor(private http: HttpClient) {
    }

    ngOnInit() {

        this.updateHistory();
        this.tableData = {
            name: "",
            headerRow: ['№', 'Длина стержня', 'Площадь поперечного сечения', 'Модуль упругости', 'Допусткаемое напряжение', 'Сосредоточенная нагрузка', 'Погонное напряжение'],
            kernels: [],
            leftSupport: false,
            rightSupport: false
        };
        this.canvas = <HTMLCanvasElement>document.getElementById('canvas');
    }

    addKernel(size: NgModel, crossSectionalArea: NgModel, elasticModulus: NgModel, allowableStress: NgModel, concentratedLoad: NgModel, linearVoltage: NgModel) {
        console.log("kern", this.tableData.kernels);
        if (size.viewModel != null && size.viewModel > 0 && crossSectionalArea.viewModel != null && elasticModulus.viewModel != null && allowableStress.viewModel != null && (this.tableData.kernels.length == 0 || this.tableData.kernels[this.tableData.kernels.length - 1].kernelSize > 0)) {
            this.tableData.kernels.push(new Kernel(size.viewModel, crossSectionalArea.viewModel,
                elasticModulus.viewModel,
                allowableStress.viewModel,
                concentratedLoad.viewModel != null ? concentratedLoad.viewModel : null,
                linearVoltage.viewModel != null ? linearVoltage.viewModel : null)
            )
            this.showNotification('success', "Cтержень успешно добавлен");
        } else if (concentratedLoad.viewModel != null && this.tableData.kernels.length > 0 && this.tableData.kernels[this.tableData.kernels.length - 1].kernelSize > 0) {
            this.tableData.kernels.push(new Kernel(size.viewModel, crossSectionalArea.viewModel,
                elasticModulus.viewModel,
                allowableStress.viewModel,
                concentratedLoad.viewModel != null ? concentratedLoad.viewModel : null,
                linearVoltage.viewModel != null ? linearVoltage.viewModel : null)
            );
            this.showNotification('success', "Последний стержень успешно добавлен");
        } else {
            this.showNotification('danger', "Не верно заполнены <b>обязательные поля</b>.");
        }
    }

    showNotification(color: string, message: string) {
        const type = ['', 'info', 'success', 'warning', 'danger'];

        $.notify({
            icon: "pe-7s-gift",
            message: message
        }, {
            type: color,
            timer: 1000,
            placement: {
                from: "top",
                align: 'center'
            }
        });
    }

    visual() {
        this.showVisualisation = true;
        this.canvas = <HTMLCanvasElement>document.getElementById('canvas');
        this.ctx = this.canvas.getContext('2d');
        this.canvas.width = 1900;
        this.canvas.height = 600;
        this.ctx.clearRect(0, 0, this.ctx.canvas.width, this.ctx.canvas.height);

        console.log("ok", this.tableData);
        if (this.canvas.getContext) {
            this.ctx.clearRect(0, 0, this.ctx.canvas.width, this.ctx.canvas.height);
            let width = 0;
            let widthOfFirst = 1000 - 10;
            let heightOfFirst = 500 - 10;
            let currentHeight = 0;
            let totalLengthL = 0;
            for (let r = 0; r < this.tableData.kernels.length; r++) {
                totalLengthL += this.tableData.kernels[r].kernelSize;
            }
            let totalSquareA = 0;
            for (let r = 0; r < this.tableData.kernels.length; r++) {
                if (totalSquareA < this.tableData.kernels[r].crossSectionalArea) {
                    totalSquareA = this.tableData.kernels[r].crossSectionalArea;
                }
            }
            let coefL = 0;
            let coefA = 0;
            coefL = (widthOfFirst - 40) / totalLengthL;
            coefA = (heightOfFirst - 40) / totalSquareA;
            //
            let picFleft = document.getElementById('picFleft');
            let picFright = document.getElementById("picFright");
            let picQleft = document.getElementById("picQleft");
            let picQright = document.getElementById("picQright");
            let maxHeight = 0;

            //console.log(picFleft);

            // @ts-ignore
            // this.ctx.drawImage(picFright, 10, 10);
            let X = 50;
            let Y = 70;
            let startX = X + 20;
            var startY = Y;
            var endX = 0;
            var endY = 0;
            var halfOfFirst = 0;
            var halfOfLast = 0;
            console.log("ok1", this.tableData.kernels);
            for (var r = 0; r < this.tableData.kernels.length; r++) {
                width = this.tableData.kernels[r].kernelSize * coefL;
                currentHeight = this.tableData.kernels[r].crossSectionalArea * coefA;

                if (currentHeight > maxHeight) {
                    maxHeight = currentHeight;
                }

                var halfOfCurrentHeight = currentHeight / 2;
                let x = X;
                endX = X;
                endY = Y;
                let y = Y + currentHeight / 2;

                var widthF = 20;
                var heightF = 70;
                var yF = y - 25;


                if (!this.tableData.kernels[r].kernelSize || !this.tableData.kernels[r].crossSectionalArea) {
                    this.showNotification('danger', "Ошибка! Длина или площадь стержня равна нулю!");
                    return;
                } else {
                    // let widthOfFirst = 1400;
                    let xOfFirst = 50;
                    let yOfFirst = 50;
                    let halfOfY = 300;
                    //console.log(xOfFirst, yOfFirst, widthOfFirst, heightOfFirst)
                    //this.ctx.strokeRect(xOfFirst, yOfFirst, widthOfFirst, heightOfFirst);
                    X = widthOfFirst;
                    let xQ = startX;
                    let yQ = halfOfY - 20;
                    let widthQ = 15;
                    let heightQ = 10;
                    this.ctx.strokeRect(startX, yOfFirst + heightOfFirst / 2 - currentHeight / 2, width, currentHeight);
                    startX += width;
                    if (this.tableData.kernels[r].concentratedLoad) {

                        // startY += currentHeight;
                        if (this.tableData.kernels[r].concentratedLoad > 0) {
                            do {
                                // @ts-ignore
                                this.ctx.drawImage(picQright, xQ, yOfFirst + heightOfFirst / 2 - heightQ / 2, widthQ, heightQ);
                                xQ += widthQ;
                            } while (xQ + widthQ <= startX);
                        } else if (this.tableData.kernels[r].concentratedLoad < 0) {
                            do {
                                // @ts-ignore
                                this.ctx.drawImage(picQleft, xQ, yOfFirst + heightOfFirst / 2 - heightQ / 2, widthQ, heightQ);
                                xQ += widthQ;
                            } while (xQ + widthQ <= startX);
                        }
                    }

                    let endOfFirst = widthOfFirst + 30;

                    if (this.tableData.leftSupport == true) {
                        // @ts-ignore
                        this.ctx.drawImage(picFleft, xOfFirst, yOfFirst + heightOfFirst / 2 - heightF / 2, widthF, heightF);
                    }

                    if (this.tableData.rightSupport == true) {
                        // @ts-ignore
                        this.ctx.drawImage(picFright, endOfFirst, yOfFirst + heightOfFirst / 2 - heightF / 2, widthF, heightF);
                    }
                }
            }
        }
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
        this.showVisualisation = false;
        this.tableData.kernels = [];
        this.tableData.name = "";
        this.tableData.leftSupport = false;
        this.tableData.rightSupport = false;
        name.reset();
        this.updateHistory();
        this.ctx.canvas.width = 0;
        this.ctx.canvas.height = 0;
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
                this.tableData.leftSupport = data[index].leftSupport;
                this.tableData.rightSupport = data[index].rightSupport;
                this.visual();
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
