import { Component } from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {RatingService} from "./rating.service";

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrl: './rating.component.css'
})
export class RatingComponent {
  ratings: any[] = [];
  length = 100;  // Replace with the actual total number of users
  pageSize = 20;
  pageSizeOptions = [5, 10, 20, 50, 100];
  pageEvent: PageEvent = {pageIndex: 0, pageSize: this.pageSize, length: this.length};
  selectedRating: any = null;
  searchID: string = '';
  rating: any = null;

  constructor(private ratingService: RatingService) { }

  ngOnInit(): void {
    this.loadRatings();
  }

  loadRatings(): void {
    this.ratingService.getRatings(this.pageEvent.pageIndex, this.pageEvent.pageSize).subscribe({
      next: ratings => {
        this.ratings = ratings.content;
        this.length = ratings.totalElements;
      },
      error: error => {
        console.error('Error loading ratings:', error);
      }
    });
  }

  searchRating(): void {
    this.ratingService.searchRating(this.searchID).subscribe({
      next: rating => {
        this.rating = rating;
      },
      error: error => {
        console.error('Error searching for rating:', error);
      }
    });
  }

  updateRating(rating: any): void {
    this.selectedRating = {...rating};
  }

  submitUpdate(): void {
    this.ratingService.updateRating(this.selectedRating.id, this.selectedRating).subscribe({
      next: () => {
        this.loadRatings();
        this.selectedRating = null;
      },
      error: error => {
        console.error('Error updating rating:', error);
      }
    });
  }

  deleteRating(rating: any): void {
    this.ratingService.deleteRating(rating.id).subscribe({
      next: () => {
        this.loadRatings();
      },
      error: error => {
        console.error('Error deleting rating:', error);
      }
    });
  }

  cancelUpdate(): void {
    this.selectedRating = null;  // Clear the selection
  }

}
