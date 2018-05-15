<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Validator;
use Tymon\JWTAuth\Exception\JWTException;
use App\User;
use App\Course;
use App\Courses_user;
use App\Feedback;
use Auth;
use JWTAuth;
use DateTime;
use Carbon\Carbon;
use Illuminate\Contracts\Encryption\DecryptException;


class UserController extends Controller
{


    //auth
    public function userLogin(Request $request){
        $validator = Validator::make($request->all(), [
            'email' => 'required|email',
            'password' => 'required',
            'state' => 'required',
        ]);

        // if ($validator->fails()) {
        //     return response()->json(['error'=>$validator->errors()], 401);            
        // }

        if(Auth::attempt(['email' => request('email'), 'password' => request('password')])){
            if(Auth::attempt(['email' => request('email'), 'password' => request('password'), 'state' => $request->state])){

            $user = Auth::user();
            // $success['token'] =  $user->createToken('MyApp')->accessToken;
            $credentials = [
            'email' => $request->email,
            'password' => $request->password,
            'state' => $request->state,
                ];
        try {
            // attempt to verify the credentials and create a token for the user
            if (! $token = JWTAuth::attempt($credentials)) {
                return response()->json(['success' => false, 'error' => 'Invalid Credentials. Please make sure you entered the right information and you have verified your email address.'], 400);
            }
        } catch (JWTException $e) {
            // something went wrong whilst attempting to encode the token
            return response()->json(['success' => false, 'error' => 'could_not_create_token'], 500);
        }
        return response()->json(['success' => true, 'token' => $token, 'id' => $user->id]);
    }else{
        return response()->json(['success' => false],401); //wrong state
    }
    }else {
        return response()->json(['success' => false],400); //wrong password or email

    }

        //     $success['id'] = $user->id;
        //     return response()->json($success);
        // }
        // else{
        //     return response()->json(['error'=>'Unauthorised'], 401);
        // }
}

    public function CheckEmailUserRegister($email){
        $message['error'] = 'Email has already registered';

        if (User::where('email',$email)->first()) {
            // return response()->json($message, 200);
            return response()->json($message, 401);
        } else{
            $success['status'] =  'ok';
        return response()->json($success, 200);

        }

    }

    // public function updateImage(Request $request){
    //      $validator = Validator::make($request->all(), [
    //         'user_id' => 'required',
    //         'id_card' => 'required',
    //     ]);

    //     $id_card = $request->file('id_card');
    //     $user_id = $request->get('user_id');
    //     $file       = $id_card;
    //     $extension  = strtolower($file->getClientOriginalExtension());
    //     $file_name  = date('YmdHis').rand().'.'.$extension;
    //     $destinationPath  = "images/uploads/IDcard_image";
    //     $file->move($destinationPath, $file_name);
    //     $urlServer = "http://localhost/UPint/public/images/uploads/IDcard_image/".$file_name;
    //     $user = User::where('id', $user_id)->update(['id_card' => $urlServer]);
    

    // }

    public function userRegister(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'first_name' => 'required',
            'last_name' => 'required',
            'email' => 'required|email',
            'password' => 'required',
            'c_password' => 'required|same:password',
            'phone_number' => 'required',
            'school' => 'required',
            'major' => 'required',
            'state' => 'required',
            'Date_graduated' => 'required',
            'image' => 'required',
        ]);

        $userEmail = $request->input('email');
        $message['error'] = 'Email has already registered';

        if (User::where('email',$userEmail)->first()) {
            // return response()->json($message, 200);
            return response()->json($message, 401);
        }

        else if ($validator->fails()) {
            return response()->json(['error'=>$validator->errors()], 401);            
        }
        else{
        $input = $request->all();
        $input['password'] = bcrypt($input['password']);
        $user = User::create($input);

        // ////image
        //  if($request->hasFile('image')) {
        $image = $request->file('image');
        $file       = $image;
        $extension  = strtolower($file->getClientOriginalExtension());
        $file_name  = date('YmdHis').rand().'.'.$extension;
        $destinationPath  = "images/uploads/Userprofileimage";
        $file->move($destinationPath, $file_name);
  


        $urlServer = "http://localhost/UPint/public/images/uploads/Userprofileimage/".$file_name;
        // $user->image = (md5($urlServer));
        $user->image = $urlServer;
        $user->save();

        // $success['token'] =  $user->createToken('MyApp')->accessToken;
        $success['user_id'] =  $user->id;
        return response()->json($success, 200);
        }
        
    }

    public function teacherRegister(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'first_name' => 'required',
            'last_name' => 'required',
            'email' => 'required|email',
            'password' => 'required',
            'c_password' => 'required|same:password',
            'phone_number' => 'required',
            'school' => 'required',
            'major' => 'required',
            'state' => 'required',
            'Date_graduated' => 'required',
            'image' => 'required',
            'location_city' => 'required',
            'university_can_teach' => 'required',
            'id_card' => 'required',
            'introduce' => 'required',
        ]);

        $userEmail = $request->input('email');
        $message['error'] = 'Email has already registered';

        if (User::where('email',$userEmail)->first()) {
            // return response()->json($message, 200);
            return response()->json($message, 401);
        }

        else if ($validator->fails()) {
            return response()->json(['error'=>$validator->errors()], 401);            
        }
        else{
        $input = $request->all();
        $input['password'] = bcrypt($input['password']);
        $user = User::create($input);

        ////image
        $image = $request->file('image');
        $file       = $image;
        $extension  = strtolower($file->getClientOriginalExtension());
        $file_name  = date('YmdHis').rand().'.'.$extension;
        $destinationPath  = "images/uploads/Userprofileimage";
        $file->move($destinationPath, $file_name);
        $urlServer = "http://localhost/UPint/public/images/uploads/Userprofileimage/".$file_name;
        $user->image = $urlServer;
        $user->save();

        // ////id card image
        $id_card = $request->file('id_card');
        $file_       = $id_card;
        $extension_  = strtolower($file_->getClientOriginalExtension());
        $file_name_  = date('YmdHis').rand().'.'.$extension_;
        $destinationPath_  = "images/uploads/IDcard_image";
        $file_->move($destinationPath_, $file_name_);
        $urlServer_ = "http://localhost/UPint/public/images/uploads/IDcard_image/".$file_name_;
        $user->id_card = $urlServer_;
        $user->save();

        // $success['token'] =  $user->createToken('MyApp')->accessToken;
        $success['user_id'] =  $user->id;
        return response()->json($success, 200);
        }
        
    }

    public function logout(Request $request) {
        $this->validate($request, ['token' => 'required']);
        try {
            JWTAuth::invalidate($request->input('token'));
            return response()->json(['success' => true]);
        } catch (JWTException $e) {
            // something went wrong whilst attempting to encode the token
            return response()->json(['success' => false, 'error' => 'Failed to logout, please try again.'], 500);
        }
    }






    //Users
    public function userDetails()
    {
        // $users = User::All();
        $users = \DB::table('users')->get();
        // $users->image = decrypt($users->image);
        // $decode_image = decrypt($users->image);
        // $users->image = $decode_image;
        // // $decode_image = (md5($users->image));
        // $users['info'] = User::All();
        // $user = new User();
        // $decode_image = (md5($user->image));
        // $users['image'] = $decode_image;


        // $users = json_decode($users);
        // foreach($users as $image){
        // if (isset($image->Type1)) {
        // return $image->Type1;
        // };
    
        return response()->json($users);
    }

    public function userDetailswithId($id)
    {
        $users  = \DB::table('users')->where('id',$id)->first();
    


        return response()->json($users);
    }

    public function getTeacherInformation(){
        $users = User::get();
        foreach ($users as $key => $value) {
            if ($value->state == 'Student') {
                unset($users[$key]);
            }
        }
        $result = $users->values()->all();

        return response()->json($result);

    }




    //Course
    public function getAllcourse()
    {
        $courses = Course::all();

        $current = Carbon::now();
        $current = new Carbon();
        $current->setTimezone('Asia/Bangkok');

        $format = 'Y-m-d H:i:s';
        foreach ($courses as $key => $value) {
        $check_start_date = DateTime::createFromFormat($format, $value->date.' '.$value->start_time);
        $start_date = new Carbon($check_start_date->format('Y-m-d H:i:s'));
        $courses[$key]['count'] = 0;
        if ($current->gt($start_date)) {
            unset($courses[$key]);
         }
     }

        $result = $courses->values()->all();
        return response()->json($result);
        
    }

    public function getCourseforHistory()
    {
        $courses = Course::all();
        return response()->json($courses);
        
    }


    public function Allcoursedetail()
    {
        $courseslist = Course::all();
            //     $courses = Course::all();
        $current = Carbon::now();
        $current = new Carbon();
        $current->setTimezone('Asia/Bangkok');
      
    
    //     return response()->json($courses);
         foreach ($courseslist as $key => $value) {
            if (Courses_user::where('course_id', '=', $value->id)->count() >= $value->total_student) {
                unset($courseslist[$key]);
            }
            $format = 'Y-m-d H:i:s';
            $check_start_date = DateTime::createFromFormat($format, $value->date.' '.$value->start_time);
            $start_date = new Carbon($check_start_date->format('Y-m-d H:i:s'));
            if ($current->gt($start_date)) {
                unset($courseslist[$key]);
            }

        }

        $result = $courseslist->values()->all();
    
        return response()->json($result);
    }

    public function classCount($course_id)
    {
        $users  = \DB::table('courses_users')->where('course_id',$course_id)->count();
        $count['count'] = $users;

        return $count;
    }

    public function classCountStudentenroll(Request $request)
    {
        $user_idInput = $request->input('user_id');
        $course_idInput = $request->input('course_id');
        $idList = array();
        $user_id = Courses_user::select('user_id')->where('user_id',$request->get('user_id'))->get();
        $course_id = Courses_user::select('course_id')->where('user_id',$request->get('user_id'))->get();
            foreach ($user_id as $key => $value) {
                $idList[$key] = $user_id[$key]['user_id'];
                $idList[$key] = $course_id[$key]['course_id'];
            }
            foreach ($idList as $key => $value) {
                if ($idList[$key] != $course_idInput) {
                    unset($idList[$key]);
                }
            }
            $result = array_values($idList);
            $count = array_count_values($result);
            foreach ($result as $key => $value) {
                $final_result['count'] = $count[$result[$key]];
            }

        return response()->json($final_result, 201);
    }

     public function coursedetailwithId($id)
    {
        $users  = \DB::table('courses')->where('id',$id)->first();
        // $format = 'Y-m-d H:i:s';
        // $check_start_date = DateTime::createFromFormat($format, $users->date.' '.$users->start_time);
        // $check_end_date   = DateTime::createFromFormat($format, $users->finish_time);

        // $start_date = new Carbon($check_start_date->format('Y-m-d H:i:s'));
        // $current = Carbon::now();
        // $current = new Carbon();
        // $current->setTimezone('Asia/Bangkok');


    
        return response()->json($users);
    }


//////
     public function coursesearchwithnameandtag(Request $request)
     {

        $name = $request->input('name');
        $tag = $request->input('tag');
        $term = $request->input('term');
        $users  = \DB::table('courses')->where('course_name', $name)
                                        ->orWhere('tags', $tag)
                                        ->orWhere('terms', $term)->get();

    
        return response()->json($users);
    }

    public function courseNameandId()
    {
        $users  = Course::select('id','course_name')->get();


    
        return response()->json($users);
    }



    public function BookClass(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'user_id' => 'required',
            'course_id' => 'required',
            'numberStudent' => 'required',
        ]);
         if ($validator->fails()) {
            return response()->json(['error'=>$validator->errors()], 401);            
        }else{
            $user_idInput = $request->get('user_id');
            $course_idInput = $request->get('course_id');
            $input = $request->all();

            $idList = array();
            $user_id = Courses_user::select('user_id')->where('user_id',$request->get('user_id'))->get();
            $course_id = Courses_user::select('course_id')->where('user_id',$request->get('user_id'))->get();
            foreach ($user_id as $key => $value) {
                $idList[$key]['user_id'] = $user_id[$key]['user_id'];
                $idList[$key]['course_id'] = $course_id[$key]['course_id'];
            }

            foreach ($idList as $key => $value) {
                if ($idList[$key]['user_id'] == $user_idInput && $idList[$key]['course_id'] == $course_idInput) {
                    return response()->json(['success' => false], 202); //already regis 
                }
            }
            $class_count = Course::where('id', '=', $request->get('course_id'))->get();
            foreach($class_count as $class_count){
                if (isset($class_count->total_student)) {
                    $count = $class_count->total_student;
                };
             }
        if (Courses_user::where('course_id', '=', $request->get('course_id'))->count() >= $count) {
            return response()->json(['success' => false], 201); //course is full
        }
        if ($request->get('numberStudent') + Courses_user::where('course_id', '=', $request->get('course_id'))->count() > $count) {
            return response()->json(['success' => false], 201); //course is full
        }

        for ($i=0; $i < $request->get('numberStudent') ; $i++) { 
            $course = Courses_user::create($input);
        }
            return response()->json(['success' => true], 200);
        }






    // public function BookClass(Request $request)
    // {
    //     $validator = Validator::make($request->all(), [
    //         'user_id' => 'required',
    //         'course_id' => 'required',
    //         'numberStudent' => 'required',
    //     ]);
    //      if ($validator->fails()) {
    //         return response()->json(['error'=>$validator->errors()], 401);            
    //     }else{
    //         $input = $request->all();
    //     if (Courses_user::where('user_id', '=', $request->get('user_id'))->count() > 0 & Courses_user::where('course_id', '=', $request->get('course_id'))->count() > 0) {
    //         return response()->json(['success' => false], 202);  //already regis 
    //     }
    //         $class_count = Course::where('id', '=', $request->get('course_id'))->get();
    //         foreach($class_count as $class_count){
    //             if (isset($class_count->total_student)) {
    //                 $count = $class_count->total_student;
    //             };
    //          }
    //     if (Courses_user::where('course_id', '=', $request->get('course_id'))->count() >= $count) {
    //         return response()->json(['success' => false], 201); //course is full
    //     }
    //     if ($request->get('numberStudent') + Courses_user::where('course_id', '=', $request->get('course_id'))->count() > $count) {
    //         return response()->json(['success' => false], 201); //course is full
    //     }


    //     for ($i=0; $i < $request->get('numberStudent') ; $i++) { 
    //         $course = Courses_user::create($input);
    //     }

    //         return response()->json(['success' => true], 200);
    //     }

        //not finish and not right 


        // $data = array();
        // $course  = \DB::table('courses_users')->where('user_id',$user_id)->pluck('course_id')->toArray();
        // foreach ($course as $key => $value) {
        //         $data[] = Course::where('id',$value)->select('id','date','start_time','finish_time')->get();
                
        //     }

        // $current = Carbon::now();
        // $current = new Carbon();
        // $current->setTimezone('Asia/Bangkok');
        // $current_date = $current->format('d-m-Y');

        // $class_enroll = Course::where('id', '=', 35)->select('id','date','start_time','finish_time')->get();
        // foreach ($class_enroll as $key => $value) {
        //     $format = 'Y-m-d H:i:s';
        //     $check_enroll_date = Carbon::createFromFormat($format, $value->date.' '.$value->start_time);
        //     $enroll_date = new Carbon($check_enroll_date->format('Y-m-d H:i:s'));
        //     $enroll_start_time = $enroll_date->format('H:i:s');
        //     $enroll_start_date = $enroll_date->format('d-m-Y');
        //     $enroll_finish_time = Carbon::createFromFormat($format, $value->date.' '.$value->finish_time)->format('H:i:s');
        // }

        // foreach ($data as $key => $value) {
        //     foreach ($value as $key2 => $value2) {
        //         $start_time = $value2->start_time;
        //         $finish_time = $value2->finish_time;
        //         $format = 'Y-m-d H:i:s';
        //         $check_start_date = Carbon::createFromFormat($format, $value2->date.' '.$start_time);
        //         $start_date = new Carbon($check_start_date->format('Y-m-d H:i:s'));
        //         $start_dateST = Carbon::createFromFormat($format, $value2->date.' '.$start_time)->format('d-m-Y');
        //         $start_timeST = Carbon::createFromFormat($format, $value2->date.' '.$start_time)->format('H:i:s');
        //         $finish_timeST = Carbon::createFromFormat($format, $value2->date.' '.$finish_time)->format('H:i:s');
        //         if ($start_date->gt($enroll_date)) {
                                        
        //         }else{ 
        //             array_splice($data,$key2,$key);
        //         }
        //         // if ($start_time < $finish_time) {
        //         //     $check = true;
        //         // }else{
        //         //     $check = false;
        //         // }
        //     }
                
        // }
        // $result = $data;
        // return response()->json($data);      
    }



    public function SearchCourseusers($user_id)
    {

        $course  = \DB::table('courses_users')->select('user_id','course_id')->where('user_id',$user_id)->get();
        $vals_ = array();
        $vals_ = $course->values()->all();

        $vals_all = array_values($vals_);
        $count = array();
        $result = array();
        $final_result = array();
        foreach ($vals_all as $key => $value) {
            $users  = \DB::table('courses_users')->where('course_id',$value->course_id)->count();
            $count[$key] = $value->course_id;
            $vals = array_count_values($count);
        }
        $vals_ = array_unique($vals_, SORT_REGULAR);
        $vals_sort = array_values($vals_);
        foreach ($vals_ as $key => $value) {
            $result[$key]['course_id'] = $value->course_id;
            $result[$key]['count'] = $vals[$value->course_id];
        }

        $final_result = array_values($result);
        return response()->json($final_result);

        
        // if (Courses_user::where('user_id', '=', $user_id)->count() > 0 & Courses_user::where('course_id', '=', $course_id)->count() > 0) {
        //     return response()->json(['success' => 'false'], 200);
        // }else{
        // return response()->json(['success' => 'true'], 200);
        // }      
    }

    public function courseListening($user_id)
    {
        $users  = \DB::table('courses')->where('user_id',$user_id)->get();

        $current = Carbon::now();
        $current = new Carbon();
        $current->setTimezone('Asia/Bangkok');

        $format = 'Y-m-d H:i:s';
        foreach ($users as $key => $value) {
        $check_start_date = DateTime::createFromFormat($format, $value->date.' '.$value->start_time);
        $start_date = new Carbon($check_start_date->format('Y-m-d H:i:s'));
        if ($current->gt($start_date)) {
            unset($users[$key]);
         }
     }

        $result = $users->values()->all();

    
        return response()->json($result);
    }


     public function addClass(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'course_name' => 'required',
            'date' => 'required|date',
            'description' => 'required',
            'start_time' => 'required|date_format:H:i',
            'finish_time' => 'required|date_format:H:i',
            'user_id' => 'required',
            'price_per_student' => 'required',
            'target_university' => 'required',
            'target_major' => 'required',
            'target_years' => 'required',
            'terms' => 'required',
            'level_of_difficult' => 'required',
            'total_student' => 'required',
            'tags' => 'required',
            'place' => 'required',
            'course_image' => 'required',
        ]);

        if ($validator->fails()) {
            return response()->json(['error'=>$validator->errors()], 401);            
        }else{
            $input = $request->all();
            $course = Course::create($input);


            $image = $request->file('course_image');
            $file       = $image;
            $extension  = strtolower($file->getClientOriginalExtension());
            $file_name  = date('YmdHis').rand().'.'.$extension;
            $destinationPath  = "images/uploads/Classimage";
            $file->move($destinationPath, $file_name);
  


            $urlServer = "http://localhost/UPint/public/images/uploads/Classimage/".$file_name;
            $course->course_image = $urlServer;
            $course->save();



            $success['status'] = 'success';
            $success['meeting_id'] =  $course->id;
            $success['course_name'] =  $course->course_name;
            $success['user_id'] =  $course->user_id;
            return response()->json($success, 200);
        }
    }





    //Search history
    public function addSearchRecent(Request $request){
        $validator = Validator::make($request->all(), [
            'id' => 'required',
            'search_recent' => 'required',
        ]);
        if ($validator->fails()) {
            return response()->json(['error'=>$validator->errors()], 401);            
        }else{
            $user_id = $request->input('id');
            $search_recent = $request->input('search_recent');
            if (User::find($user_id)) {
            $users = User::where('id', $user_id)->update(['search_recent' => $search_recent]);
            return response()->json(['success' => true], 200);   
            }else{
            return response()->json(['success' => false], 201);
            }
        }

    }

    public function getSerchRecent($user_id){
        $class_count = User::where('id', '=', $user_id)->get();
            foreach($class_count as $class_count){
                if (isset($class_count->search_recent)) {
                    $search_recent = $class_count->search_recent;
                    return response()->json(['search_recent' => $search_recent],200);
                };
             }
             return response()->json(['search_recent' => ''],201);

    }



    //Feedback 

    public function addCommenttoFeedback(Request $request) {
        $validator = Validator::make($request->all(), [
            'user_id' => 'required',
            'comment' => 'required',
        ]);

        if ($validator->fails()) {
            return response()->json(['error'=>$validator->errors()], 401);            
        }else{
            if (User::where('id', '=', $request->get('user_id'))->count() > 0) {
                $comment = $request->input('comment');
                $user_id = $request->input('user_id');
                if ($feedback = Feedback::where('user_id', '=', $request->get('user_id'))->count() > 0) {
                    $feedback = Feedback::where('user_id', $user_id)->update(['comment' => $comment]);
                    return response()->json(['success' => true], 200);
                }else{
                    $input = $request->all();
                    $feedback = Feedback::create($input);
                    return response()->json(['success' => true], 200);
                }
            }else {
                return response()->json(['false' => true], 201);
            }
        
        }
    }

    public function getCommentfromFeedback(Request $request) {
            if (Feedback::where('user_id', '=', $request->get('user_id'))->count() > 0) {
                $feedback = Feedback::get();
                foreach($feedback as $feedback){
                if (isset($feedback->comment)) {
                    $comment = $feedback->comment;
                return response()->json(['comment' => $comment], 200);
            }
        }
            }else {
                return response()->json([], 201);
            }
    }




    //Statistic
    public function array_sort_by_column(&$array, $column, $direction = SORT_ASC) {
        $reference_array = array();

        foreach($array as $key => $row) {
            $reference_array[$key] = $row[$column];
        }

        array_multisort($reference_array, $direction, $array);
        }

    // public function getStatClasscountWith7days(Request $request){
        
    //     $users  = \DB::table('courses')->where('user_id',$request->get('user_id'))->get();

    //     $current = Carbon::now();
    //     $current->setTimezone('Asia/Bangkok');

    //     $tomorrow = Carbon::now();


    //     $format = 'Y-m-d';
    //     $data = array();
    //     foreach ($users as $key => $value) {
    //     $check_start_date = DateTime::createFromFormat($format, $value->date);
    //     $start_date = new Carbon($check_start_date->format('Y-m-d'));
    //     if ($start_date->gt($current)) {
    //         unset($users[$key]);
    //      }
    //     }
    //     $users_result = $users->values()->all();
    //     $format2 = 'Y-m-d';
    //     foreach ($users_result as $key => $value) {
    //     $checkstartdate = DateTime::createFromFormat($format2, $value->date);
    //     $startdate = new Carbon($checkstartdate->format('Y-m-d'));
    //     $result[$startdate->toDateString()] = $startdate->toDateString();
    //     $data[$key] = $result[$startdate->toDateString()];
    //     // if ($result[$startdate->toDateString()] == null) {
    //     //     $result[$startdate->toDateString()] = $startdate;
    //     // }else{
    //     //     $result[$startdate->toDateString()] = $startdate;
    //     // }    
    // }
    //     $vals = array_count_values($data);
    //     $vals_ = array();
    //     $int_value = 0;
    //     foreach ($data as $key => $value) {
    //     $vals_[$key]['date'] = $data[$key];
    //     $int_value = (int)$vals[$data[$key]];
    //     $vals_[$key]['count'] = $int_value;
    // }
    //     $vals_ = array_unique($vals_, SORT_REGULAR);
    //     $final_result = array_values($vals_);
    //     $this->array_sort_by_column($final_result, 'date');

    //     return response()->json($final_result, 200);
    // }

    public function getAllStatClasscount(Request $request){
        
        $users  = \DB::table('courses')->where('user_id',$request->get('user_id'))->get();

        $current = Carbon::now();
        $current->setTimezone('Asia/Bangkok');

        $tomorrow = Carbon::tomorrow();


        $format = 'Y-m-d H:i:s';
        $data = array();

        $users_result = $users->values()->all();
        $format2 = 'Y-m-d';
        foreach ($users_result as $key => $value) {
        $checkstartdate = DateTime::createFromFormat($format2, $value->date);
        $startdate = new Carbon($checkstartdate->format('Y-m-d'));
        $result[$startdate->toDateString()] = $startdate->toDateString();
        $data[$key] = $result[$startdate->toDateString()];
        // if ($result[$startdate->toDateString()] == null) {
        //     $result[$startdate->toDateString()] = $startdate;
        // }else{
        //     $result[$startdate->toDateString()] = $startdate;
        // }    
        }
        $vals = array_count_values($data);
        $vals_ = array();
        foreach ($data as $key => $value) {
            $vals_[$key]['date'] = $data[$key];
            $vals_[$key]['count'] = $vals[$data[$key]];
        }
        $vals_ = array_unique($vals_, SORT_REGULAR);
        $final_result = array_values($vals_);

        $this->array_sort_by_column($final_result, 'date');

    

        return response()->json($final_result, 200);
    }

    public function getAllStatprice(Request $request){
        
        $users  = \DB::table('courses')->where('user_id',$request->get('user_id'))->get();

        $current = Carbon::now();
        $current->setTimezone('Asia/Bangkok');

        $tomorrow = Carbon::tomorrow();


        $format = 'Y-m-d H:i:s';
        $data = array();
        $price = array();
        $array_3 = array();

        $users_result = $users->values()->all();
        $format2 = 'Y-m-d';

        foreach ($users_result as $key => $value) {
        $class_enroll = \DB::table('courses_users')->where('course_id',$value->id)->count();
        if ($class_enroll > 0) {
        $checkstartdate = DateTime::createFromFormat($format2, $value->date);
        $startdate = new Carbon($checkstartdate->format('Y-m-d'));
        $result[$startdate->toDateString()] = $startdate->toDateString();
        $data[$key] = $result[$startdate->toDateString()];
        $price[$key] = ($value->price_per_student)*($class_enroll);

         }else{
            unset($users_result[$key]);
         }    
        }
        $vals_ = array();
        foreach ($data as $key => $value) {
            $vals_[$key]['date'] = $data[$key];
            $vals_[$key]['price_per_student'] = $price[$key];
        }
        $final_result = array_values($vals_);

        $this->array_sort_by_column($final_result, 'date');
        $collection = collect($final_result);
        $st = isset($collection[0]) ? $collection[0] : false;
        if ($st){
        $date_first = $collection[0]['date'];
        foreach ($collection as $key => $value) {
            if ($key == 0) {
                $array_3[$key]['date'] = $collection[$key]['date'];
                $array_3[$key]['price_per_student'] = $collection[$key]['price_per_student'];
            }else{
                $index = $key-1;
                for ($i=$index; $i >= 0 ; $i--) {
                if($collection[$key]['date'] == $collection[$index]['date']){ 
                    $price_first = $collection[$index]['price_per_student'];
                    $price_first = $price_first + $collection[$key]['price_per_student'];
                    $array_3[$key]['date'] = $collection[$key]['date'];
                    $array_3[$key]['price_per_student'] = $price_first;
                    $array_3[$index]['price_per_student']= $price_first;
                    break;
                }else{
                $array_3[$key]['date'] = $collection[$key]['date'];
                $array_3[$key]['price_per_student'] = $collection[$key]['price_per_student'];
                break;
                    }
                }
            }
        }
    }

        $array_3 = array_unique($array_3, SORT_REGULAR);
        $array_3_result = array_values($array_3);
        return response()->json($array_3_result, 200);
    }

    public function getAllStatHour(Request $request){
        
        $users  = \DB::table('courses')->where('user_id',$request->get('user_id'))->get();

        $format = 'Y-m-d H:i:s';
        $data = array();
        $hour = array();
        $array_3 = array();

        $users_result = $users->values()->all();

        foreach ($users_result as $key => $value) {
        $class_enroll = \DB::table('courses_users')->where('course_id',$value->id)->count();
        if ($class_enroll > 0) {
        $checkstartdate = DateTime::createFromFormat($format, $value->date.' '.$value->start_time);
        $startdate = new Carbon($checkstartdate->format('Y-m-d H:i:s'));
        $checkfinishdate = DateTime::createFromFormat($format, $value->date.' '.$value->finish_time);
        $finishdate = new Carbon($checkfinishdate->format('Y-m-d H:i:s'));
        $result[$startdate->toDateString()] = $startdate->toDateString();
        $data[$key] = $result[$startdate->toDateString()];
        $hour[$key] = ($finishdate->diffInMinutes($startdate))/60;

         }else{
            unset($users_result[$key]);
         }    
        }
        $vals_ = array();
        foreach ($data as $key => $value) {
            $vals_[$key]['date'] = $data[$key];
            $vals_[$key]['hour'] = $hour[$key];
        }
        $final_result = array_values($vals_);

        $this->array_sort_by_column($final_result, 'date');
        $collection = collect($final_result);
        $st = isset($collection[0]) ? $collection[0] : false;
        if ($st){
        $date_first = $collection[0]['date'];
        foreach ($collection as $key => $value) {
            if ($key == 0) {
                $array_3[$key]['date'] = $collection[$key]['date'];
                $array_3[$key]['hour'] = $collection[$key]['hour'];
            }else{
                $index = $key-1;
                for ($i=$index; $i >= 0 ; $i--) {
                if($collection[$key]['date'] == $collection[$index]['date']){ 
                    $price_first = $collection[$index]['hour'];
                    $price_first = $price_first + $collection[$key]['hour'];
                    $array_3[$key]['date'] = $collection[$key]['date'];
                    $array_3[$key]['hour'] = $price_first;
                    $array_3[$index]['hour']= $price_first;
                    break;
                }else{
                $array_3[$key]['date'] = $collection[$key]['date'];
                $array_3[$key]['hour'] = $collection[$key]['hour'];
                break;
                    }
                }
            }
        }
    }

        $array_3 = array_unique($array_3, SORT_REGULAR);
        $array_3_result = array_values($array_3);
        return response()->json($array_3_result, 200);
    }

//     public function get7daysStatprice(Request $request){
        
//         $users  = \DB::table('courses')->where('user_id',$request->get('user_id'))->get();

//         $current = Carbon::now();
//         $current->setTimezone('Asia/Bangkok');

//         $tomorrow = Carbon::tomorrow();




//         $format = 'Y-m-d';
//         $data = array();
//         $price = array();
//         $array_3 = array();
//         foreach ($users as $key => $value) {
//         $check_start_date = DateTime::createFromFormat($format, $value->date);
//         $start_date = new Carbon($check_start_date->format('Y-m-d'));
//         if ($start_date->gt($current)) {
//             unset($users[$key]);
//          }
//         }

//         $users_result = $users->values()->all();
//         $format2 = 'Y-m-d';

//         foreach ($users_result as $key => $value) {
//         $class_enroll = \DB::table('courses_users')->where('course_id',$value->id)->count();
//         if ($class_enroll > 0) {
//         $checkstartdate = DateTime::createFromFormat($format2, $value->date);
//         $startdate = new Carbon($checkstartdate->format('Y-m-d'));
//         $result[$startdate->toDateString()] = $startdate->toDateString();
//         $data[$key] = $result[$startdate->toDateString()];
//         $price[$key] = ($value->price_per_student)*($class_enroll);

//          }else{
//             unset($users_result[$key]);
//          }    
//         }
//         $vals_ = array();
//         foreach ($data as $key => $value) {
//             $vals_[$key]['date'] = $data[$key];
//             $vals_[$key]['price_per_student'] = $price[$key];
//         }
//         $final_result = array_values($vals_);

//         $this->array_sort_by_column($final_result, 'date');
//         $collection = collect($final_result);
//         $st = isset($collection[0]) ? $collection[0] : false;
//         if ($st){
//         $date_first = $collection[0]['date'];
//         foreach ($collection as $key => $value) {
//             if ($key == 0) {
//                 $array_3[$key]['date'] = $collection[$key]['date'];
//                 $array_3[$key]['price_per_student'] = $collection[$key]['price_per_student'];
//             }else{
//                 $index = $key-1;
//                 for ($i=$index; $i >= 0 ; $i--) {
//                 if($collection[$key]['date'] == $collection[$index]['date']){ 
//                     $price_first = $collection[$index]['price_per_student'];
//                     $price_first = $price_first + $collection[$key]['price_per_student'];
//                     $array_3[$key]['date'] = $collection[$key]['date'];
//                     $array_3[$key]['price_per_student'] = $price_first;
//                     $array_3[$index]['price_per_student']= $price_first;
//                     break;
//                 }else{
//                 $array_3[$key]['date'] = $collection[$key]['date'];
//                 $array_3[$key]['price_per_student'] = $collection[$key]['price_per_student'];
//                 break;
//                     }
//                 }
//             }
//         }
// }

//         $array_3 = array_unique($array_3, SORT_REGULAR);
//         return response()->json($array_3, 200);
//     }

}
