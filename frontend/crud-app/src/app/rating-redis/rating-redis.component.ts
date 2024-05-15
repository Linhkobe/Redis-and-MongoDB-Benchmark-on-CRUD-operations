import { Component } from '@angular/core';
import { PageEvent } from "@angular/material/paginator";
import { RatingRedisService } from "./rating-redis.service";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-rating-redis',
  templateUrl: './rating-redis.component.html',
  styleUrls: ['./rating-redis.component.css']
})
export class RatingRedisComponent {
  ratings: any[] = [];
  length = 100;  // Replace with the actual total number of users
  pageSize = 20;
  pageSizeOptions = [5, 10, 20, 50, 100];
  pageEvent: PageEvent = { pageIndex: 0, pageSize: this.pageSize, length: this.length };
  selectedRating: any = null;
  searchID: string = '';
  rating: any = null;
  ratingForm : FormGroup;
  createFormVisible: boolean = false;

  constructor(private ratingRedisService: RatingRedisService,private fb: FormBuilder) {
    this.ratingForm = this.fb.group({
      id: [''],
      movie_id: [''],
      rating_val: [''],
      user_id: ['']
    });
  }

  ngOnInit(): void {
    this.loadRatings();
  }

  loadRatings(): void {
    this.ratingRedisService.getRatings(this.pageEvent.pageIndex, this.pageEvent.pageSize).subscribe({
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
    this.ratingRedisService.searchRating(this.searchID).subscribe({
      next: rating => {
        this.rating = rating;
      },
      error: error => {
        console.error('Error searching for rating:', error);
      }
    });
  }

  updateRating(rating: any): void {
    this.selectedRating = { ...rating };
  }

  submitUpdate(): void {
    this.ratingRedisService.updateRating(this.selectedRating.id, this.selectedRating).subscribe({
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
    this.ratingRedisService.deleteRating(rating.id).subscribe({
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

  toggleCreateForm() {
    this.createFormVisible = !this.createFormVisible;
  }

  //onsubmit du formulaire create
  onSubmit() {
    this.toggleCreateForm();
    console.log(this.ratingForm.value);
    this.ratingRedisService.createRating(this.ratingForm.value).subscribe({
      next: (response) => console.log('Rating created successfully!', response),
      error: (error) => console.error('Failed to create rating', error)
    });
  }
}


