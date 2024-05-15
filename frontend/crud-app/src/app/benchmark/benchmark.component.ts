import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { BenchmarkService } from "./benchmark.service";
import { NgForm } from "@angular/forms";
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-benchmark',
  templateUrl: './benchmark.component.html',
  styleUrls: ['./benchmark.component.css']
})
export class BenchmarkComponent implements OnInit {
  showDashboard = false;

<<<<<<< Updated upstream
  selectedMovie: any="";

=======
  resultsFindMovie: any[] = [];
>>>>>>> Stashed changes
  resultsm: any[] = [];
  resultsr: any[] = [];
  resultsu: any[] = [];
  displayedColumns: string[] = ['averageTimeElapsedMongo', 'averageTimeElapsedInMillisecondsMongo', 'averageTimeElapsedRedis', 'averageTimeElapsedInMillisecondsRedis', 'averageCpuLoad', 'runs'];
  @ViewChild('movieForm') movieForm!: NgForm;
<<<<<<< Updated upstream
  @ViewChild('updateForm') updateForm!: NgForm;

=======
  @ViewChild('ratingForm') ratingForm!: NgForm;
  @ViewChild('userForm') userForm!: NgForm;
>>>>>>> Stashed changes

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
    const count = this.ratingForm.value.count;
    const runs = this.ratingForm.value.runs;

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
    const count = this.userForm.value.count;
    const runs = this.userForm.value.runs;

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

<<<<<<< Updated upstream

  updateMovies() {
    if (this.updateForm.valid) {
      const count = this.updateForm.value.count;
      const runs = this.updateForm.value.runs;
      this.benchmarkService.updateMovies(count, runs).subscribe({
        next: (response) => {
          console.log('Updated successfully:', response);
          this.resultsm.push(response); // Mettre à jour l'affichage des résultats
        },
        error: (error) => {
          console.error('Update error:', error);
        }
      });
    }
  }

=======
  findMovies() {

    const count = this.movieForm.value.count;
    const runs = this.movieForm.value.runs;

    this.benchmarkService.findMovies(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response); // Log the response
        this.resultsFindMovie = [response]; // Wrap the response in an array
        this.SharedService.updateBenchmarkData(response);
        this.showDashboard = true;
      },
      error: (error) => {
        console.error('Error:', error); // Log any errors
      }
    });
  }

  findRatings() {
    const count = this.ratingForm.value.count;
    const runs = this.ratingForm.value.runs;

    this.benchmarkService.findRatings(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response); // Log the response
        this.resultsr = [response]; // Wrap the response in an array
      },
      error: (error) => {
        console.error('Error:', error); // Log any errors
      }
    });
  }

  findUsers() {
    const count = this.userForm.value.count;
    const runs = this.userForm.value.runs;

    this.benchmarkService.findUsers(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response); // Log the response
        this.resultsu = [response]; // Wrap the response in an array
      },
      error: (error) => {
        console.error('Error:', error); // Log any errors
      }
    });
  }
>>>>>>> Stashed changes
}
