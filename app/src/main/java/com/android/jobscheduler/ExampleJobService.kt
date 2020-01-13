package com.android.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class ExampleJobService :JobService( ){
    companion object{
        val TAG = "ExampleJobService"
        var jobCancelled = false
    }
    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG,"Job Cancelled Before Completion")
        jobCancelled = true //we are responsible of stopping the job our self when the onStopJob Is called

        return true //we want to reschedule the job if the job is cancelled
    }

    override fun onStartJob(params: JobParameters?): Boolean {



        Log.d(TAG,"Job Started")
        doBackgroundWork(params)


        return true //we are responsible of telling the OS when our service is finished
    }

    fun doBackgroundWork(params:JobParameters?) {
        Thread(Runnable {
            (0 until 10).forEach { i->
                if(jobCancelled){ //if onStopJob is called we have to cancel the job manually
                return@Runnable
                }
                Log.d(TAG,"run: $i")
                Thread.sleep(1000)
            }
            Log.d(TAG,"Job Finished")
            jobFinished(params,false) //since we returned true in onStartJob, we are responsible of telling the OS when the job is finished,otherwise we will drain the battery
        }).start()

    }
}