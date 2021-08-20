package com.iraqsoft.mawgood.views.customClock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.iraqsoft.mawgood.R
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class CustomClock(context: Context?) : View(context) {


 private var clockHeight  = 0
 private var clockWidth = 0
 private var padding =0
 private var fontSize = 0f
 private var numeralSpacing = 0
 private var handTruncation = 0
 private var  hourHandTruncation = 0
 private var  radius = 0
 private lateinit var paint: Paint
 private var isInit = false
 private val numArray = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12)
 private  var  rect = Rect()


    private  fun initClock(){
        clockHeight = height
        clockWidth = width
        padding = numeralSpacing+50
        fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,13f,resources.displayMetrics)
      val minimum =  min(clockHeight,clockWidth)
        radius = minimum /  2-padding
        handTruncation = minimum/20
        hourHandTruncation = minimum / 7
        paint =Paint()
        isInit = true

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(!isInit){
            initClock()
        }
        canvas?.drawColor(Color.WHITE)
        drawCircle(canvas)
        drawCenter(canvas)
        drawNumeral(canvas)
        drawHands(canvas)

    }


    private fun drawCircle(canvas: Canvas?) {
        paint.apply {
            reset()
            color = ContextCompat.getColor(context, R.color.lightBlueClock)
            strokeWidth = 20f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
        canvas?.drawCircle((width/2).toFloat(), (height/2).toFloat(), (radius+padding-10).toFloat(),paint)
    }

    private fun drawCenter(canvas: Canvas?) {
        paint.apply {
           style = Paint.Style.FILL

        }
        canvas?.drawCircle((width/2).toFloat(),(height/2).toFloat(),12f,paint)
    }

    private fun drawNumeral(canvas: Canvas?) {
       paint.textSize = fontSize
        for (number in numArray){
           val  stringOfNumber = number.toString()
            paint.getTextBounds(stringOfNumber,0,stringOfNumber.length,rect)
            val angle = Math.PI/6 * (number-3)
            val x = (width/2 + cos(angle)*radius-rect.width()/2).toFloat()
            val y = (width/2 + sin(angle)*radius+rect.width()/2).toFloat()
            canvas?.drawText(stringOfNumber,x,y,paint)
        }

    }

    private  fun drawHand(canvas: Canvas?,loc:Float,isHour:Boolean)
    {
          val angle = Math.PI * loc /30 -Math.PI/2
        val handRadius = if(isHour) radius-handTruncation-hourHandTruncation else radius-handTruncation
        canvas?.drawLine((width/2).toFloat(),(height/2).toFloat(),
        ((width/2)+cos(angle)*handRadius).toFloat(),((height/2)+sin(angle)*handRadius).toFloat(),paint)
    }
    private fun drawHands(canvas: Canvas?) {
        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR_OF_DAY).toFloat()
        hour  = if(hour > 12 ){hour -12}else{hour}
        drawHand(canvas,(hour+calendar.get(Calendar.MINUTE)/60) * 5f,true)
        drawHand(canvas,calendar.get(Calendar.MINUTE).toFloat(),false)
        drawHand(canvas,calendar.get(Calendar.SECOND).toFloat(),false)
    }




}