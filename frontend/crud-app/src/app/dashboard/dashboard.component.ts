// dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
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
      type: 'line',
      data: {
        datasets: [
          {
            label: 'Redis',
            data: [],
            borderColor: 'red',
            fill: false
          },
          {
            label: 'MongoDB',
            data: [],
            borderColor: 'blue',
            fill: false
          }
        ]
      },
      options: {
        scales: {
          x: { // Use 'x' instead of 'xAxes'
            type: 'linear',
            position: 'bottom',
            beginAtZero: true
          }
        }
      }
    });
  }
}
