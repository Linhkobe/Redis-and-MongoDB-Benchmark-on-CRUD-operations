import { Component, OnInit, ViewChild } from '@angular/core';
import { BenchmarkService } from './benchmark.service';
import { NgForm } from '@angular/forms';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-benchmark',
  templateUrl: './benchmark.component.html',
  styleUrls: ['./benchmark.component.css']
})
export class BenchmarkComponent implements OnInit {
  showDashboardMovie = false;
  showDashboardRating = false;
  showDashboardUser = false;
  resultsFindMovie: any[] = [];
  resultsm: any[] = [];
  resultsr: any[] = [];
  resultsu: any[] = [];
  displayedColumns: string[] = ['averageTimeElapsedMongo', 'averageTimeElapsedInMillisecondsMongo', 'averageTimeElapsedRedis', 'averageTimeElapsedInMillisecondsRedis', 'averageCpuLoad', 'runs'];

  @ViewChild('movieForm') movieForm!: NgForm;
  @ViewChild('ratingForm') ratingForm!: NgForm;
  @ViewChild('userForm') userForm!: NgForm;
  @ViewChild('updateForm') updateForm!: NgForm;

  constructor(private benchmarkService: BenchmarkService, private sharedService: SharedService) {}

  ngOnInit(): void {}

  createMovies() {
    const count = this.movieForm.value.count;
    const runs = this.movieForm.value.runs;

    this.benchmarkService.createMovies(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsm = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardMovie = true;
        this.showDashboardRating = false;
        this.showDashboardUser = false;
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }

  createRatings() {
    const count = this.ratingForm.value.count;
    const runs = this.ratingForm.value.runs;

    this.benchmarkService.createRatings(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsr = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardMovie = false;
        this.showDashboardRating = true;
        this.showDashboardUser = false;
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }

  createUsers() {
    const count = this.userForm.value.count;
    const runs = this.userForm.value.runs;

    this.benchmarkService.createUsers(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsu = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardMovie = false;
        this.showDashboardRating = false;
        this.showDashboardUser = true;
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }

  updateMovies() {
    if (this.updateForm.valid) {
      const count = this.updateForm.value.count;
      const runs = this.updateForm.value.runs;

      this.benchmarkService.updateMovies(count, runs).subscribe({
        next: (response) => {
          console.log('Updated successfully:', response);
          this.resultsm.push(response);
        },
        error: (error) => {
          console.error('Update error:', error);
        }
      });
    }
  }

  findMovies() {
    const count = this.movieForm.value.count;
    const runs = this.movieForm.value.runs;

    this.benchmarkService.findMovies(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsFindMovie = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardMovie = true;
        this.showDashboardRating = false;
        this.showDashboardUser = false;
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }

  findRatings() {
    const count = this.ratingForm.value.count;
    const runs = this.ratingForm.value.runs;

    this.benchmarkService.findRatings(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsr = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardMovie = false;
        this.showDashboardRating = true;
        this.showDashboardUser = false;
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }

  findUsers() {
    const count = this.userForm.value.count;
    const runs = this.userForm.value.runs;

    this.benchmarkService.findUsers(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsu = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardMovie = false;
        this.showDashboardRating = false;
        this.showDashboardUser = true;
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }
}
