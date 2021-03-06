package com.example.aihack.utils

import android.graphics.*
import com.google.mlkit.vision.face.Face
import android.util.Log
import com.google.mlkit.vision.face.FaceContour


class FaceContourGraphic(
    overlay: GraphicOverlay,
    private val face: Face,
    private val imageRect: Rect
) : GraphicOverlay.Graphic(overlay) {

    private val facePositionPaint: Paint
    private val idPaint: Paint
    private val boxPaint: Paint

    init {
        val selectedColor = Color.WHITE

        facePositionPaint = Paint()
        facePositionPaint.color = selectedColor

        idPaint = Paint()
        idPaint.color = selectedColor

        boxPaint = Paint()
        boxPaint.color = selectedColor
        boxPaint.style = Paint.Style.STROKE
        boxPaint.strokeWidth = BOX_STROKE_WIDTH
    }

    override fun draw(canvas: Canvas?) {
        for (contour in 8..11) {
            val values = face.getContour(contour)!!.points
            val paths = calculatePath(
                imageRect.height().toFloat(),
                imageRect.width().toFloat(), values
            )
            val path = Path()
            for (i in 0 until paths.count() - 1) {
                path.moveTo(paths[i][0], paths[i][1])
                path.lineTo(paths[i + 1][0], paths[i + 1][1])
            }
            canvas?.drawPath(path, boxPaint)
            path.close()
        }
        val loverValues = face.getContour(FaceContour.LOWER_LIP_TOP)!!.points
        val upperValues = face.getContour(FaceContour.UPPER_LIP_BOTTOM)!!.points
        val diff = arrayListOf<Float>()
        for (i in 0 until loverValues.size) {
            diff.add(loverValues[i].y - upperValues[i].y)
        }
        if (diff.average() > 2)
            MOUTH_OPENED = true
        Log.d("FDS", diff.average().toString())
    }

    companion object {
        private const val BOX_STROKE_WIDTH = 5.0f
        var MOUTH_OPENED = false
    }

}
