import { Component } from '@angular/core';
import {SharedService} from "../shared.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  chart: any;

  constructor(private sharedService: SharedService) { }

  ngOnInit(): void {
    this.sharedService.benchmarkData$.subscribe(data => {
      if (data) {
        this.chart.data.datasets[0].data.push(data.averageTimeElapsedInMillisecondsRedis);
        this.chart.data.datasets[1].data.push(data.averageTimeElapsedInMillisecondsMongo);
        this.chart.update();
      }
    });
    this.chart = new Chart('canvas', {
      type: '',
      data: {
        datasets: []
      }
    });
  }

}
