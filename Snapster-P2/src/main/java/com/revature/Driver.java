package com.revature;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.f4b6a3.uuid.UuidCreator;
import com.revature.model.FriendRequest;
import com.revature.model.Photo;
import com.revature.model.User;
import com.revature.model.UserPosts;
import com.revature.service.S3Service;
import com.revature.service.SnapsterService;

public class Driver {
	SnapsterService service = new SnapsterService();
	S3Service s3service = new S3Service();

	public static void main(String[] args) {
		
		Driver driver = new Driver();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		driver.service = ctx.getBean("snapsterService", SnapsterService.class);
		driver.s3service = ctx.getBean("s3service",S3Service.class);
		
		// driver.insertUser();
		//driver.validateUser();
		//driver.insertPhoto();
		//driver.getPhotos();
		//driver.insertRequest();
		//driver.deleteRequest();
		//driver.insertRequest();
		//driver.getFriendRequests();
		//driver.addImageToS3();
		//driver.getAllMyFriends();
		//driver.getMyPendingFriendRequests();
		driver.uploadImageFullProcess();
		//driver.getAllUserPosts();
	}

	public void getFriendRequests() {
		String receiver = "user2";
		ArrayList<FriendRequest> friends = service.getFriendRequests(receiver);
		System.out.println("No.of FriendRequests: " + friends.size());
		for (FriendRequest friend : friends) {
			System.out.println(
					"Friend for : " + friend.getReceiver() + " : " + friend.getSender() + ", isApproved: " + friend.isApproved());
		}
	}

	public void insertRequest() {
		String user1 = "user1";
		String user2 = "user2";
		FriendRequest req = new FriendRequest(user1, user2, false);
		service.insertFriendRequest(req);
		service.approveRequest(req);
	}
	
	public void deleteRequest() {
		String user1 = "user1";
		String user2 = "user2";
		FriendRequest req = new FriendRequest(user1, user2, false);
		service.deleteRequest(req);
		
	}
	
	
	public void insertUser() {
		String username = "user2";
		String password = "pwd";
		String email = "test";
		String firstname = "user2FirstName";
		String lastname = "user2LastName";
		Date date = new Date();

		User user = new User(username, password, email, firstname, lastname, date);

		service.insertUser(user);

	}

	public void validateUser() {
		String username = "user2";
		String password = "pwd";

		User validateUser1 = service.validateUser(username, password);
		System.out.println("user " + username + ", valid test1 :" + validateUser1);

		password = "pwd2";

		User validateUser2 = service.validateUser(username, password);
		System.out.println("user " + username + ", valid test2:" + validateUser2);

	}

	public void insertPhoto() {
		Photo photo = new Photo("user1", "PhotoLocation1", "caption", new Date());
		Photo photo2 = new Photo("user1", "PhotoLocation2", "caption", new Date());
		Photo photo3 = new Photo("user1", "PhotoLocation3", "caption", new Date());

		service.insertPhoto(photo);
		service.insertPhoto(photo2);
		service.insertPhoto(photo3);
	}

	
	
	public void getPhotos() {

		ArrayList<Photo> photos = service.getPhotos("rupesh", true);

		System.out.println("No.of Photos: " + photos.size());
		for (Photo photo : photos) {
			System.out.println("Photo for : " + photo.getUsername() + ", location: " + photo.getFilename()+ ", location: " + photo.getCaption());
		}

	}
	
	public void addImageToS3() {

		File file = new File("example.png");

		//s3service.putObject(file);
	}
	
	public void getAllMyFriends() {

		ArrayList<User> users = service.getAllMyFriends("user1");

		System.out.println("No.of Users: " + users.size());
		for (User user : users) {
			System.out.println("Friend : " + user.getUsername() + ", FirstName: " + user.getFirstname());
		}

	}
	
	public void getMyPendingFriendRequests() {
		ArrayList<User> users = service.getMyPendingFriendRequests("rupesh");

		System.out.println("No.of Users: " + users.size());
		for (User user : users) {
			System.out.println("Friends Not Approved: " + user.getUsername() + ", FirstName: " + user.getFirstname());
		}
	}
	public void uploadImageFullProcess() {
		//user uploads photo
		File image = new File("Receipt1.PNG");
		//server-side validation occurs
		
		//Photo object is created with username, new filename, and date
		String uuid = UuidCreator.getTimeOrdered().toString();
		Photo photo = new Photo("user1", uuid, "caption", new Date());
		//Photo is inserted into database
		service.insertPhoto(photo);
		//Photo is added to S3 bucket
		s3service.putObject(image, uuid);
	}
	
	public void getAllUserPosts() {
		ArrayList<UserPosts> userPosts = service.getAllUserPosts("user2",true);

		System.out.println("No.of Posts: " + userPosts.size());
		for (UserPosts userPost : userPosts) {
			System.out.println("Posts: " + userPost.getUsername() + ", post: " + userPost.getPost() + ", time: " + userPost.getSenttime());
		}
		
		userPosts = service.getAllUserPosts("user2",false);

		System.out.println("No.of Posts: " + userPosts.size());
		for (UserPosts userPost : userPosts) {
			System.out.println("Posts: " + userPost.getUsername() + ", post: " + userPost.getPost() + ", time: " + userPost.getSenttime());
		}
	}
}
