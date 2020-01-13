package com.android.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobScheduler.RESULT_SUCCESS
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG= "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun scheduleJob(v: View){
        val componentName = ComponentName(this,ExampleJobService::class.java)
        val jobInfo = JobInfo.Builder(123,componentName)
            .setRequiresCharging(true) //we require that the phone is charging
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) //we require a Wifi Network
            .setPersisted(true) //keep the job Alive even if we reboot the device
            .setPeriodic(15*60*1000) //how often we want to execute the job, but its not exact,cannot be less than 15 minutes since android Nougat, so even if we set it less than 15 mins it will be 15 min internally
            .build()
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val result = scheduler.schedule(jobInfo)  //Returns an int representing (RESULT_SUCCESS or RESULT_FAILURE
        if(result == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG,"Job Scheduled")
        }else{
            Log.d(TAG,"Job Scheduling Failed")
        }
    }
    fun cancelJob(v:View){
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(123)
        Log.d(TAG,"Job Cancelled")

    }
}
