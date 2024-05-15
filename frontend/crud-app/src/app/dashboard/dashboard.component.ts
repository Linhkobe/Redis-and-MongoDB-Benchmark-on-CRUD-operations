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
    const benchmarkCount = this.chart.data.labels.length + 1;

    // Ajouter les valeurs MongoDB et Redis
    this.chart.data.labels.push(`Benchmark ${benchmarkCount}`);
    this.chart.data.datasets[0].data.push({
      x: benchmarkCount,
      y: data.averageTimeElapsedInMillisecondsMongo
    });
    this.chart.data.datasets[1].data.push({
      x: benchmarkCount,
      y: data.averageTimeElapsedInMillisecondsRedis
    });

    this.chart.update();
  }

  initChart(): void {
    this.chart = new Chart('canvas', { // Assurez-vous que l'ID 'canvas' existe dans votre HTML
      type: 'line',
      data: {
        datasets: [
          {
            label: 'MongoDB',
            data: [],
            borderColor: 'blue',
            fill: false
          },
          {
            label: 'Redis',
            data: [],
            borderColor: 'red',
            fill: false
          }
        ],
        labels: [] // Les labels seront ajoutés dynamiquement à chaque nouveau benchmark
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            type: 'linear',
            position: 'bottom',
            min:1,
            max:34,
            title: {
              display: true,
              text: 'Benchmark Number'
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
