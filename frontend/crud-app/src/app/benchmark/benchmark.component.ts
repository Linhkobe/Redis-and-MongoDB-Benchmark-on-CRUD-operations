import { Component, OnInit, ViewChild } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BenchmarkService} from "./benchmark.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-benchmark',
  templateUrl: './benchmark.component.html',
  styleUrls: ['./benchmark.component.css']
})
export class BenchmarkComponent implements OnInit {

  result: any;
  results: any[] = [];
  displayedColumns: string[] = ['database', 'time', 'cpu', 'averageTimeElapsed', 'averageTimeElapsedInMilliseconds', 'averageCpuLoad', 'runs'];

  @ViewChild('movieForm') movieForm!: NgForm;

  constructor(private benchmarkService: BenchmarkService) { }

  ngOnInit(): void {
  }

  createMovies() {
    const count = this.movieForm.value.count;
    const runs = this.movieForm.value.runs;

    this.benchmarkService.createMovies(count, runs).subscribe({
      next: (response) => {
        console.log('Response:', response); // Log the response
        this.results = response;
      },
      error: (error) => {
        console.error('Error:', error); // Log any errors
      }
    });
  }
}
