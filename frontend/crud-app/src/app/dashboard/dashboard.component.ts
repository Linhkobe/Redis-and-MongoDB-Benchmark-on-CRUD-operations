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
  benchmarkCount: number = 0;

  constructor(private sharedService: SharedService) {
    Chart.register(...registerables); // Assurez-vous d'enregistrer les composants Chart.js requis
  }

  ngOnInit(): void {
    this.initChart();
    this.sharedService.benchmarkData$.subscribe(data => {
      if (data) {
        this.benchmarkCount++;
        this.addChartData(data, this.benchmarkCount);
      }
    });
  }

  addChartData(data: any, benchmarkCount: number) {
    // Ajoutez la valeur Mongo comme point bleu
    this.chart.data.datasets[0].data.push({
      x: benchmarkCount,
      y: data.averageTimeElapsedInMillisecondsMongo,
      backgroundColor: 'blue'
    });

    // Ajoutez la valeur Redis comme point rouge
    this.chart.data.datasets[1].data.push({
      x: benchmarkCount,
      y: data.averageTimeElapsedInMillisecondsRedis,
      backgroundColor: 'red'
    });

    // Ajouter une ligne reliant les points
    if (benchmarkCount > 1) {
      const prevBenchmarkCount = benchmarkCount - 1;
      this.chart.data.datasets[0].data.push({
        x: prevBenchmarkCount,
        y: data.averageTimeElapsedInMillisecondsMongo,
        backgroundColor: 'transparent'
      });
      this.chart.data.datasets[1].data.push({
        x: prevBenchmarkCount,
        y: data.averageTimeElapsedInMillisecondsRedis,
        backgroundColor: 'transparent'
      });
    }

    this.chart.update();
  }

  initChart(): void {
    this.chart = new Chart('canvas', { // Assurez-vous que l'ID 'canvas' existe dans votre HTML
      type: 'scatter',
      data: {
        datasets: [
          {
            label: 'MongoDB',
            data: [],
            pointRadius: 5,
            pointHoverRadius: 8,
            pointStyle: 'circle',
            borderColor: 'blue',
            backgroundColor: 'transparent',
            showLine: true,
            fill: false,
          },
          {
            label: 'Redis',
            data: [],
            pointRadius: 5,
            pointHoverRadius: 8,
            pointStyle: 'rectRot',
            borderColor: 'red',
            backgroundColor: 'transparent',
            showLine: true,
            fill: false,
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
