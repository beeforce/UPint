<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AddInformationCourses extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('courses', function($table) {
        $table->string('target_university');
        $table->string('target_major');
        $table->string('target_years');
        $table->string('terms');
        $table->string('level_of_difficult');
        $table->integer('total_student');
        $table->string('tags');
        $table->string('place');
        $table->text('course_image');


    });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
         Schema::table('courses', function($table) {
       $table->string('target_university');
        $table->string('target_major');
        $table->string('target_years');
        $table->string('terms');
        $table->string('level_of_difficult');
        $table->integer('total_student');
        $table->string('tags');
        $table->string('place');
        $table->text('course_image');
    });
    }
}
