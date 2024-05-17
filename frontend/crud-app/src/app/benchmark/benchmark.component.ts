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
  showDashboardCreateMovie = false;
  showDashboardCreateRating = false;
  showDashboardCreateUser = false;
  showDashboardUpdateMovie = false;
  showDashboardUpdateRating = false;
  showDashboardUpdatUser = false;
  showDashboardFindMovie = false;
  showDashboardFindRating = false;
  showDashboardFindUser = false;
  showDashboardDeleteMovie = false;
  resultsFindMovie: any[] = [];
  resultsFindRating: any[] = [];
  resultsFindUser: any[] = [];
  resultsUpdateMovie: any[] = [];
  resultsUpdateRatings: any[] = [];
  resultsUpdateUsers: any[] = [];
  resultsCreateMovie: any[] = [];
  resultsCreateRating: any[] = [];
  resultsCreateUser: any[] = [];
  resultsDeleteMovie: any[] = [];

  displayedColumns: string[] = ['averageTimeElapsedMongo', 'averageTimeElapsedInMillisecondsMongo', 'averageTimeElapsedRedis', 'averageTimeElapsedInMillisecondsRedis', 'averageCpuLoad', 'runs'];

  @ViewChild('movieForm') movieForm!: NgForm;
  @ViewChild('ratingForm') ratingForm!: NgForm;
  @ViewChild('userForm') userForm!: NgForm;
  @ViewChild('updateForm') updateForm!: NgForm;
  @ViewChild ('updateMovieForm') updateMovieForm!: NgForm;
  @ViewChild ('updateRatingForm') updateRatingForm!: NgForm;
  @ViewChild('updateUserForm') updateUserForm!: NgForm;
  @ViewChild('findRatingForm') findRatingForm!: NgForm;
  @ViewChild('findMovieForm') findMovieForm!: NgForm;
  @ViewChild('findUserForm') findUserForm!: NgForm;
  @ViewChild('deleteMovieForm') deleteMovieForm!: NgForm;


  constructor(private benchmarkService: BenchmarkService, private sharedService: SharedService) {}

  ngOnInit(): void {}

  createMovies() {
    const count = this.movieForm.value.count;
    const runs = this.movieForm.value.runs;

    this.benchmarkService.createMovies(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsCreateMovie = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardCreateMovie = true;
        this.showDashboardCreateRating = false;
        this.showDashboardCreateUser = false;
        this.showDashboardUpdateMovie = false;
        this.showDashboardUpdateRating = false;
        this.showDashboardUpdatUser = false;
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
        this.resultsCreateRating = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardCreateMovie = false;
        this.showDashboardCreateRating = true;
        this.showDashboardCreateUser = false;
        this.showDashboardUpdateMovie = false;
        this.showDashboardUpdateRating = false;
        this.showDashboardUpdatUser = false;

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
        this.resultsCreateUser = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardCreateMovie = false;
        this.showDashboardCreateRating = false;
        this.showDashboardCreateUser = true;
        this.showDashboardUpdateMovie = false;
        this.showDashboardUpdateRating = false;
        this.showDashboardUpdatUser = false;

      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }

  updateMovies() {
    if (this.updateMovieForm.valid) {
      const count = this.updateMovieForm.value.count;
      const runs = this.updateMovieForm.value.runs;

      this.benchmarkService.updateMovies(count, runs).subscribe({
        next: (response) => {
          console.log('Response:', response);
          this.resultsUpdateMovie = [response];
          this.sharedService.updateBenchmarkData(response);
          this.showDashboardCreateMovie = false;
          this.showDashboardCreateRating = false;
          this.showDashboardCreateUser = false;
          this.showDashboardUpdateMovie = true;
          this.showDashboardUpdateRating = false;
          this.showDashboardUpdatUser = false;
        },
        error: (error) => {
          console.error('Error:', error);
        }
      });
    }
  }
  updateRatings() {
    if (this.updateRatingForm.valid) {
      const count = this.updateRatingForm.value.count;
      const runs = this.updateRatingForm.value.runs;

      this.benchmarkService.updateRatings(count, runs).subscribe({
        next: (response) => {
          console.log('Response:', response);
          this.resultsUpdateRatings = [response];
          this.sharedService.updateBenchmarkData(response);
          this.showDashboardCreateMovie = false;
          this.showDashboardCreateRating = false;
          this.showDashboardCreateUser = false;
          this.showDashboardUpdateMovie = false;
          this.showDashboardUpdateRating = true;
          this.showDashboardUpdatUser = false;

        },
        error: (error) => {
          console.error('Error:', error);
        }
      });
    }
  }
  updateUsers() {
    if (this.updateUserForm.valid) {
      const count = this.updateUserForm.value.count;
      const runs = this.updateUserForm.value.runs;

      this.benchmarkService.updateUsers(count, runs).subscribe({
        next: (response) => {
          console.log('Response:', response);
          this.resultsUpdateUsers = [response];
          this.sharedService.updateBenchmarkData(response);
          this.showDashboardCreateMovie = false;
          this.showDashboardCreateRating = false;
          this.showDashboardCreateUser = false;
          this.showDashboardUpdateMovie = false;
          this.showDashboardUpdateRating = false;
          this.showDashboardUpdatUser = true;

        },
        error: (error) => {
          console.error('Error:', error);
        }
      });
    }
  }

  findMovies() {
    const count = this.findMovieForm.value.count;
    const runs = this.findMovieForm.value.runs;

    this.benchmarkService.findMovies(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsFindMovie = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardCreateMovie = false;
        this.showDashboardCreateRating = false;
        this.showDashboardCreateUser = false;
        this.showDashboardUpdateMovie = false;
        this.showDashboardUpdateRating = false;
        this.showDashboardUpdatUser = false;
        this.showDashboardFindMovie = true;
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }


  findRatings() {
    const count = this.findRatingForm.value.count;
    const runs = this.findRatingForm.value.runs;

    this.benchmarkService.findRatings(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsFindRating = [response];
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardCreateMovie = false;
        this.showDashboardCreateRating = false;
        this.showDashboardCreateUser = false;
        this.showDashboardFindRating = true;
        this.showDashboardFindMovie = false;
        this.showDashboardFindUser = false;
        this.showDashboardUpdateMovie = false;
        this.showDashboardUpdateRating = false;
        this.showDashboardUpdatUser = false;
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }

  findUsers() {
    const count = this.findUserForm.value.count;
    const runs = this.findUserForm.value.runs;

    this.benchmarkService.findUsers(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsFindUser = [response]; //must change
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardCreateMovie = false;
        this.showDashboardCreateRating = false;
        this.showDashboardCreateUser = false;
        this.showDashboardFindRating = false;
        this.showDashboardFindMovie = false;
        this.showDashboardFindUser = true;
        this.showDashboardUpdateMovie = false;
        this.showDashboardUpdateRating = false;
        this.showDashboardUpdatUser = false;
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }
  deleteMovies() {
    const count = this.deleteMovieForm.value.count;
    const runs = this.deleteMovieForm.value.runs;

    this.benchmarkService.deleteMovies(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response);
        this.resultsDeleteMovie = [response]; //must change
        this.sharedService.updateBenchmarkData(response);
        this.showDashboardCreateMovie = false;
        this.showDashboardCreateRating = false;
        this.showDashboardCreateUser = false;
        this.showDashboardFindRating = false;
        this.showDashboardFindMovie = false;
        this.showDashboardFindUser = false;
        this.showDashboardUpdateMovie = false;
        this.showDashboardUpdateRating = false;
        this.showDashboardUpdatUser = false;
        this.showDashboardDeleteMovie = true;
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }
}
