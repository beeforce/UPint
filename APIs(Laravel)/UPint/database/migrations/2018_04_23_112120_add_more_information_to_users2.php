<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AddMoreInformationToUsers2 extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('users', function($table) {
        $table->string('location_city')->nullable();
        $table->string('university_can_teach')->nullable();
        $table->string('id_card')->nullable();
        $table->string('introduce')->nullable();

    });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('users', function($table) {
        $table->dropColumn('location_city');
        $table->dropColumn('university_can_teach');
        $table->dropColumn('id_card');
        $table->dropColumn('introduce');
    });
    }
}
