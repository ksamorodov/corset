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
    public disableSave = false;
    public index = "index.jpg";
    public kernel: Kernel = new Kernel(null, null, null, null, null, null);

    constructor(private http: HttpClient) {
    }

    ngOnInit() {

        this.updateHistory();
        this.tableData = {
            name: "",
            headerRow: ['№', 'Длина стержня(L)', 'Площадь поперечного сечения(A)', 'Модуль упругости(E)', 'Допусткаемое напряжение(б)', 'Сосредоточенная нагрузка(F)', 'Погонное напряжение(Q)'],
            kernels: [],
            leftSupport: false,
            rightSupport: false
        };
        this.canvas = <HTMLCanvasElement>document.getElementById('canvas');
    }

    addKernel(size: NgModel, crossSectionalArea: NgModel, elasticModulus: NgModel, allowableStress: NgModel, concentratedLoad: NgModel, linearVoltage: NgModel) {
        if (size.viewModel != null && size.viewModel > 0 && crossSectionalArea.viewModel != null && elasticModulus.viewModel != null && allowableStress.viewModel != null && concentratedLoad.viewModel != null && linearVoltage.viewModel != null && (this.tableData.kernels.length == 0 || this.tableData.kernels[this.tableData.kernels.length - 1].kernelSize > 0)) {
            this.tableData.kernels.push(new Kernel(size.viewModel, crossSectionalArea.viewModel,
                elasticModulus.viewModel,
                allowableStress.viewModel,
                concentratedLoad.viewModel != null ? concentratedLoad.viewModel : null,
                linearVoltage.viewModel != null ? linearVoltage.viewModel : null)
            )
            this.resetKernel(size, crossSectionalArea, elasticModulus, allowableStress, concentratedLoad, linearVoltage);
            this.showNotification('success', "Cтержень успешно добавлен");
        } else if (concentratedLoad.viewModel != null && size.viewModel == null && crossSectionalArea.viewModel == null && elasticModulus.viewModel == null && allowableStress.viewModel == null && linearVoltage.viewModel == null && this.tableData.kernels.length > 0 && this.tableData.kernels[this.tableData.kernels.length - 1].kernelSize > 0) {
            this.tableData.kernels.push(new Kernel(size.viewModel, crossSectionalArea.viewModel,
                elasticModulus.viewModel,
                allowableStress.viewModel,
                concentratedLoad.viewModel != null ? concentratedLoad.viewModel : null,
                linearVoltage.viewModel != null ? linearVoltage.viewModel : null)
            );
            this.disableSave = true;
            this.resetKernel(size, crossSectionalArea, elasticModulus, allowableStress, concentratedLoad, linearVoltage);
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

            let picPright = document.getElementById("picPright");
            let picPleft = document.getElementById("picPleft");
            let maxHeight = 0;

            let X = 50;
            let Y = 70;
            let startX = X + 20;
            var endX = 0;
            var endY = 0;
            for (var r = 0; r < this.tableData.kernels.length; r++) {
                width = this.tableData.kernels[r].kernelSize * coefL;
                currentHeight = this.tableData.kernels[r].crossSectionalArea * coefA;

                if (currentHeight > maxHeight) {
                    maxHeight = currentHeight;
                }

                endX = X;
                endY = Y;

                var widthF = 20;
                var heightF = 70;


                if (!this.tableData.kernels[r].kernelSize || !this.tableData.kernels[r].crossSectionalArea) {
                    if (r != this.tableData.kernels.length - 1) {
                        this.showNotification('danger', "Ошибка! Длина или площадь стержня равна нулю!");
                        return;
                    }
                } else {
                    let xOfFirst = 50;
                    let yOfFirst = 50;
                    X = widthOfFirst;
                    let xQ = startX;
                    let widthQ = 30;
                    let heightQ = 15;
                    this.ctx.strokeRect(startX, yOfFirst + heightOfFirst / 2 - currentHeight / 2, width, currentHeight);
                    startX += width;

                    if (this.tableData.kernels[r].concentratedLoad > 0) {
                        // @ts-ignore
                        this.ctx.drawImage(picPright, xQ, yOfFirst + heightOfFirst / 2 - (currentHeight / 3) / 2, width / 2, currentHeight / 3);
                    } else if (this.tableData.kernels[r].concentratedLoad < 0 && r > 0) {
                        // @ts-ignore
                        this.ctx.drawImage(picPleft, xQ - this.tableData.kernels[r - 1].kernelSize * coefL / 2, yOfFirst + heightOfFirst / 2 - (this.tableData.kernels[r - 1].crossSectionalArea * coefA / 3) / 2, this.tableData.kernels[r - 1].kernelSize * coefL / 2, this.tableData.kernels[r - 1].crossSectionalArea * coefA / 3);
                    }

                    if (this.tableData.kernels[r].linearVoltage > 0) {
                        do {
                            // @ts-ignore
                            this.ctx.drawImage(picQright, xQ, yOfFirst + heightOfFirst / 2 - heightQ / 2, widthQ, heightQ);
                            xQ += widthQ;
                        } while (xQ + widthQ <= startX);
                    } else if (this.tableData.kernels[r].linearVoltage < 0) {
                        do {
                            // @ts-ignore
                            this.ctx.drawImage(picQleft, xQ, yOfFirst + heightOfFirst / 2 - heightQ / 2, widthQ, heightQ);
                            xQ += widthQ;
                        } while (xQ + widthQ <= startX);
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
        if (this.tableData.name != "" && this.tableData.kernels.length != null && this.tableData.kernels[this.tableData.kernels.length - 1].kernelSize == null) {
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
        if (this.tableData.kernels.length == 1 || this.tableData.kernels[this.tableData.kernels.length - 1].kernelSize == null) {
            this.showNotification('danger', "Не введен последний стержень. Заполните только поле сосредоточенная нагрузка")
        }
    }

    getLastConstruction() {
        this.http.get<any>("/constructions/").pipe().subscribe((data) => {
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
        this.disableSave = false;
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
        this.disableSave = true;
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
