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
    Chart.register(...registerables); // Assurez-vous d'enregistrer les composants Chart.js requis
  }

  ngOnInit(): void {
    this.initChart();
    this.sharedService.benchmarkData$.subscribe(data => {
      if (data) {
        this.addChartData(data);
      }
    });
  }

  addChartData(data: any) {
    const runIndex = this.chart.data.labels.length + 1;
    this.chart.data.labels.push(runIndex.toString()); // Ajoutez l'index comme label

    // Ajoutez la valeur Mongo comme point bleu
    this.chart.data.datasets[0].data.push({
      x: runIndex,
      y: data.averageTimeElapsedInMillisecondsMongo,
      backgroundColor: 'blue'
    });

    // Ajoutez la valeur Redis comme point rouge
    this.chart.data.datasets[1].data.push({
      x: runIndex,
      y: data.averageTimeElapsedInMillisecondsRedis,
      backgroundColor: 'red'
    });

    this.chart.update();
  }

  initChart(): void {
    if (this.chart) {
      this.chart.destroy();
    }
    this.chart = new Chart('canvas', { // Assurez-vous que l'ID 'canvas' existe dans votre HTML
      type: 'scatter',
      data: {
        datasets: [
          {
            label: 'MongoDB',
            data: [],
            pointRadius: 5,
            pointHoverRadius: 8,
            pointStyle: 'circle'
          },
          {
            label: 'Redis',
            data: [],
            pointRadius: 5,
            pointHoverRadius: 8,
            pointStyle: 'rectRot'
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            type: 'linear',
            position: 'bottom',
            title: {
              display: true,
              text: 'Run Index'
            }
          },
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Average Time Elapsed (Milliseconds)'
            }
          }
        }
      },
    });
  }
}
