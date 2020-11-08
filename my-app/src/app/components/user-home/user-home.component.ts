import { Component, OnInit, ContentChildren } from '@angular/core';
import { FormBuilder, FormGroup, Validators  } from '@angular/forms';
import {FriendServiceService} from 'src/app/services/friend-service.service';
import { Router, ActivatedRoute } from '@angular/router';
import {ViewMyDetailsComponent} from 'src/app/components/view-my-details/view-my-details.component';

import { first } from 'rxjs/operators';
@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {

  @ContentChildren(ViewMyDetailsComponent)

  userHomeForm: FormGroup;
  requestFriendForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  users:Object[]
  myFriends:Object[]
  myPendingFriends:Object[]
  requestFriendList:Object[]
  myPhotos:Object[]

  constructor(
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private router: Router,
      private friendService: FriendServiceService
  ) {
      
  }

  ngOnInit() {
    
      let currentUser = localStorage.getItem("currentUser");
      if (currentUser == null) {
          this.router.navigate(["/login"])
      }
      this.userHomeForm = this.formBuilder.group({
          
      });
      this.requestFriendForm = this.formBuilder.group({
        friendusername: ['', Validators.required],
      });
      // get return url from route parameters or default to '/'
      this.returnUrl = '/home';
      
  }

  // convenience getter for easy access to form fields
  get f() { return this.userHomeForm.controls; }
  get requestFriendFunc() { return this.requestFriendForm.controls; }

  viewMyInfo() {
    console.log("viewMyInfo Clicked");
    let currentUser = localStorage.getItem("currentUser");
    this.friendService.viewMyInfo(currentUser).subscribe(
      (data) => {
        console.log(data)
        this.users = data;
        this.myFriends = [];
        this.myPendingFriends = [];
        this.requestFriendList=[];
        this.myPhotos=[];
      },
      () => {
        console.log("sorry something went wrong")
      }
    )
  }

  viewMyPhotos(){
    console.log("viewMyPhotos Clicked");
    let currentUser = localStorage.getItem("currentUser");
    this.friendService.viewMyPhotos(currentUser).subscribe(
      (data) => {
        console.log(data)
        this.users = [];
        this.myFriends = [];
        this.myPendingFriends = [];
        this.requestFriendList=[];
        this.myPhotos=data;
      },
      () => {
        console.log("sorry something went wrong")
      }
    )
  }

  viewMyFriends(){
    console.log("viewMyFriends Clicked");
    let currentUser = localStorage.getItem("currentUser");
    this.friendService.viewMyFriends(currentUser).subscribe(
      (data) => {
        console.log(data)
        this.myFriends = data;
        this.users = [];
        this.myPendingFriends = [];
        this.requestFriendList=[];
        this.myPhotos=[];
      },
      () => {
        console.log("sorry something went wrong")
      }
    )
  }  
  
  viewMyPendingFriendRequest(){
    let currentUser = localStorage.getItem("currentUser");
    this.friendService.viewMyPendingFriendRequest(currentUser).subscribe(
      (data) => {
        console.log(data)
        this.myPendingFriends = data;
        this.myFriends = [];
        this.users = [];
        this.requestFriendList=[];
        this.myPhotos=[];
      },
      () => {
        console.log("sorry something went wrong")
      }
    )
    console.log("viewMyPendingFriendRequest Clicked");
  }  

  showFriendRequestForm(){
    this.myPendingFriends = [];
    this.myFriends = [];
    this.users = [];
    this.myPhotos=[];
    this.requestFriendList = [0];
  }

  requestFriend(){
    console.log("addFriend Clicked");
    this.submitted = true;

    // stop here if form is invalid
    if (this.requestFriendForm.invalid) {
        return;
    }

    this.loading = true;
    let currentUser = localStorage.getItem("currentUser");
    this.friendService.requestFriend(currentUser, this.requestFriendFunc.friendusername.value).subscribe(
      (data) => {
        console.log(data)
        this.myPendingFriends = data;
        this.myFriends = [];
        this.users = [];
        this.requestFriendList=[];
        this.myPhotos=[];
      },
      () => {
        console.log("sorry something went wrong")
      }
    )

    //let responseData = this.friendService.validateLogin(this.f.username.value, this.f.password.value);

  }  
  
  viewMyFriendPhotos(){
    console.log("viewMyPendingFriendRequest Clicked");
  }

  approveFriend(friendUserName) {
    let currentUser = localStorage.getItem("currentUser");
    this.friendService.approveFriend(currentUser,friendUserName).subscribe(
      (data) => {
        console.log("approveFriend response: " + data)
        console.log("approveFriend response: " + data[0])
      },
      () => {
        console.log("sorry something went wrong")
      }
    )
    console.log("approveFriend Clicked");
  }

  logout(){
    let currentUser = localStorage.removeItem("currentUser");
    this.router.navigate(["/login"]);
  }
  unFriend(friendUserName) {
    let currentUser = localStorage.getItem("currentUser");
    this.friendService.unFriend(currentUser,friendUserName).subscribe(
      (data) => {
        console.log("unFriend response: " + data)
        console.log("unFriend response: " + data[0])
      },
      () => {
        console.log("sorry something went wrong")
      }
    )
    console.log("unFriend Clicked");
  }
  onSubmit() {
      this.submitted = true;

      // stop here if form is invalid
      if (this.userHomeForm.invalid) {
          return;
      }

      this.loading = true;

      console.log("Button clicked: ");
      
      //let responseData = this.friendService.validateLogin(this.f.username.value, this.f.password.value);

      //console.log("Inside logincomponentts response Data: " + responseData);
      this.router.navigate(["/home"]);

          // .pipe(first())
          // .subscribe(
          //     data => {
          //         console.log("received back: " + data);
          //         this.router.navigate(["/home"]);
          //     },
          //     error => {
                 
          //         this.loading = false;
          //     });
             
              
  }

}
