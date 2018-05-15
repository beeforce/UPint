<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

	Route::group(['middleware' => ['jwt.auth']], function() {
		//auth
    Route::get('logout', 'UserController@logout');
	});

	Route::post('login', 'UserController@userLogin');
	Route::post('register', 'UserController@userRegister');
	// Route::post('updateImage', 'UserController@updateImage');
	Route::post('teacherRegister', 'UserController@teacherRegister');
	Route::get('CheckEmailRegister/{email}','UserController@CheckEmailUserRegister');

	//User
	Route::get('userDetailswithId/{id}','UserController@userDetailswithId');
	Route::get('userDetails','UserController@userDetails');
	Route::get('getTeacherInformation','UserController@getTeacherInformation');

	//Course and class
	Route::get('Allcoursedetail','UserController@Allcoursedetail');
	Route::get('getAllcoursedetail','UserController@getAllcourse');
	Route::get('courseDetails/{id}','UserController@coursedetailwithId');
	Route::get('courseEnroll/{user_id}','UserController@SearchCourseusers');
	Route::get('getCoursename','UserController@courseNameandId');
	Route::get('classCount/{course_id}','UserController@classCount');
	Route::get('coursesearchwithnameandtag','UserController@coursesearchwithnameandtag');
	Route::get('courseListening/{user_id}','UserController@courseListening');
	Route::get('classCountStudentenroll','UserController@classCountStudentenroll');
	Route::post('bookClass', 'UserController@BookClass');
	Route::post('addClass', 'UserController@addClass');

	//Search history
	Route::get('getallCourseforHistory','UserController@getCourseforHistory');
	Route::get('getSerchRecent/{user_id}','UserController@getSerchRecent');
	Route::post('addSearchRecent', 'UserController@addSearchRecent');

	//Feedback
	Route::post('addCommenttoFeedback', 'UserController@addCommenttoFeedback');
	Route::get('getCommentfromFeedback','UserController@getCommentfromFeedback');

	//Statistic
	Route::get('getAllStatClasscount','UserController@getAllStatClasscount');
	Route::get('getAllStatprice','UserController@getAllStatprice');
	Route::get('getAllStatHour','UserController@getAllStatHour');
