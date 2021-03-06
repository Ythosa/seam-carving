package workers

import imageModifiers.ImageModifier
import imageModifiers.converters.EnergyConverter
import imageModifiers.converters.NegativeConverter
import imageModifiers.seamcarving.SeamCarving


class ActionWorker(private val data: InputData) {
    private var imageModifier: ImageModifier? = null

    init {
        if (data.inputImagePath == null || data.outputImagePath == null)
            throw Error("Input path and output path must be initialized!")

        val imageWorker = ImageWorker(data.inputImagePath, data.outputImagePath)
        val image = imageWorker.getImage()
        when (data.actionType) {
            "to-energy" -> imageModifier = EnergyConverter(image, data)
            "to-negative" -> imageModifier = NegativeConverter(image, data)
            "seam-carving" -> imageModifier = SeamCarving(image, data)
        }
    }

    fun convert() {
        val imageWorker = ImageWorker(data.inputImagePath!!, data.outputImagePath!!)

        if (imageModifier == null)
            throw Error("Invalid action name!")

        imageWorker.createImageFile(imageModifier!!.get())
    }
}
