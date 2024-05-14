import { Component } from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {UserService} from "../user/user.service";

@Component({
  selector: 'app-user-redis',
  templateUrl: './user-redis.component.html',
  styleUrl: './user-redis.component.css'
})
export class UserRedisComponent {
  users: any[] = [];
  length = 100;  // Replace with the actual total number of users
  pageSize = 20;
  pageSizeOptions = [5, 10, 20, 50, 100];
  pageEvent: PageEvent = {pageIndex: 0, pageSize: this.pageSize, length: this.length};
  selectedUser: any = null;
  searchID: string = '';
  user: any = null;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getUsers(this.pageEvent.pageIndex, this.pageEvent.pageSize).subscribe({
      next: users => {
        this.users = users.content;
        this.length = users.totalElements;
      },
      error: error => {
        console.error('Error loading users:', error);
      }
    });
  }

  searchUser(): void {
    this.userService.searchUser(this.searchID).subscribe({
      next: user => {
        this.user = user;
      },
      error: error => {
        console.error('Error searching for user:', error);
      }
    });
  }

  updateUser(user: any): void {
    this.selectedUser = {...user};  // Make a copy of the user to avoid modifying the original
  }

  submitUpdate(): void {
    this.userService.updateUser(this.selectedUser.id, this.selectedUser).subscribe({
      next: () => {
        this.loadUsers();
        this.selectedUser = null;
      },
      error: error => {
        console.error('Error updating user:', error);
      }
    });
  }

  deleteUser(id: string): void {
    this.userService.deleteUser(id).subscribe({
      next: () => {
        this.loadUsers();
      },
      error: error => {
        console.error('Error deleting user:', error);
      }
    });
  }

  cancelUpdate(): void {
    this.selectedUser = null;  // Clear the selection
  }


}

