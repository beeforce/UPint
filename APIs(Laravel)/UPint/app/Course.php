<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Course extends Model
{
    protected $fillable = ['course_name', 'description','price_per_student', 'date', 'start_time', 'finish_time', 'user_id','target_university','target_major','target_years', 'terms', 'level_of_difficult', 'total_student',
		'tags', 'place', 'course_image'];

    protected $hidden = [
        'created_at','updated_at',
    ];
}
