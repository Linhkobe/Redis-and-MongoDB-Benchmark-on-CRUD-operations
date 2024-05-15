import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  chart: any;

  constructor(private sharedService: SharedService) {
    Chart.register(...registerables); // Make sure to register the required Chart.js components
  }

  ngOnInit(): void {
    this.initChart();
    this.sharedService.benchmarkData$.subscribe(data => {
      if (data) {
        this.addChartData(data);
      }
    });
    this.sharedService.benchmarkData$.subscribe(data => {
      if (data) {
        const runIndex = this.chart.data.labels.length + 1;
        this.chart.data.labels.push(runIndex); // Ajoutez l'index comme label
        this.chart.data.datasets[0].data.push({
          x: runIndex,
          y: data.averageTimeElapsedInMillisecondsRedis
        });
        this.chart.data.datasets[1].data.push({
          x: runIndex,
          y: data.averageTimeElapsedInMillisecondsMongo
        });
        this.chart.update();
      }
    });
  }

  addChartData(data: any) {
    const newLabelIndex = this.chart.data.labels.length + 1;
    this.chart.data.labels.push(`Run ${newLabelIndex}`);
    this.chart.data.datasets[0].data.push(data.mongoTime);
    this.chart.data.datasets[1].data.push(data.redisTime);
    this.chart.update();
  }

  initChart(): void {
    if (this.chart) {
      this.chart.destroy();
    }
    this.chart = new Chart('canvas', { // Ensure the ID 'canvas' exists in your HTML
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
        ],
        labels: [] // Consider adding labels if needed
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            type: 'linear',
            position: 'bottom',
          },
          y: { // Ensure there is a 'y' scale configuration if needed
            beginAtZero: true
          }
        }
      },
    });
  }
}
