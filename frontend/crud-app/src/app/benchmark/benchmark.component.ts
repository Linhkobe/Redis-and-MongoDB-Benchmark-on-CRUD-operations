import { Component, OnInit, ViewChild } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BenchmarkService} from "./benchmark.service";
import {NgForm} from "@angular/forms";
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-benchmark',
  templateUrl: './benchmark.component.html',
  styleUrls: ['./benchmark.component.css']
})
export class BenchmarkComponent implements OnInit {
  showDashboard = false;

  resultsm: any[] = [];
  resultsr: any[] = [];
  resultsu: any[] = [];
  displayedColumns: string[] = ['averageTimeElapsedMongo', 'averageTimeElapsedInMillisecondsMongo', 'averageTimeElapsedRedis', 'averageTimeElapsedInMillisecondsRedis', 'averageCpuLoad', 'runs'];
  @ViewChild('movieForm') movieForm!: NgForm;

  constructor(private benchmarkService: BenchmarkService, private SharedService: SharedService) {
  }

  ngOnInit(): void {
  }

  createMovies() {
    const count = this.movieForm.value.count;
    const runs = this.movieForm.value.runs;

    this.benchmarkService.createMovies(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response); // Log the response
        this.resultsm = [response]; // Wrap the response in an array
        this.SharedService.updateBenchmarkData(response);
        this.showDashboard = true;
      },
      error: (error) => {
        console.error('Error:', error); // Log any errors
      }
    });
  }

  createRatings() {
    const count = this.movieForm.value.count;
    const runs = this.movieForm.value.runs;

    this.benchmarkService.createRatings(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response); // Log the response
        this.resultsr = [response]; // Wrap the response in an array
      },
      error: (error) => {
        console.error('Error:', error); // Log any errors
      }
    });
  }

  createUsers() {
    const count = this.movieForm.value.count;
    const runs = this.movieForm.value.runs;

    this.benchmarkService.createUsers(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response); // Log the response
        this.resultsu = [response]; // Wrap the response in an array
      },
      error: (error) => {
        console.error('Error:', error); // Log any errors
      }
    });
  }
}
