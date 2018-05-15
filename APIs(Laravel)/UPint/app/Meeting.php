<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Meeting extends Model
{

	// protected $table = 'carts_details';
	
    protected $fillable = ['title', 'description', 'date', 'start_time', 'finish_time', 'user_id'];

    // public function meeting(){
    // 	return $this->belongsToMany('App\User');
    // }
}
