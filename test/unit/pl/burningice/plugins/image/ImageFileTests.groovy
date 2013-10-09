package pl.burningice.plugins.image

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import pl.burningice.plugins.image.file.ImageFile
import pl.burningice.plugins.image.file.ImageFileFactory
import pl.burningice.plugins.image.file.LocalImageFile
import pl.burningice.plugins.image.file.MultipartImageFile
import pl.burningice.plugins.image.test.FileUploadUtils

import javax.media.jai.RenderedOp

/**
 * @author pawel.gdula@burningice.pl
 */
@TestMixin([FileUploadUtils, GrailsUnitTestMixin])
class ImageFileTests {

    void testProduceFile() {
        assertTrue ImageFileFactory.produce(new File(getFilePath('image.jpg'))) instanceof LocalImageFile
        assertTrue ImageFileFactory.produce(getMultipartFile('image.jpg')) instanceof MultipartImageFile
    }

    void testGetAsByteArrayFirstThenAsJaiStreamJpg() {
        ImageFile image = ImageFileFactory.produce(getMultipartFile('image.jpg'))
        byte[] byteArray = image.getAsByteArray()
        assertEquals 514893, byteArray.size()
        RenderedOp jaiStream = image.getAsJaiStream()
        assertEquals 1920, jaiStream.width
        assertEquals 1200, jaiStream.height
        byteArray = image.getAsByteArray()
        assertEquals 514893, byteArray.size()
        jaiStream = image.getAsJaiStream()
        assertEquals 1920, jaiStream.width
        assertEquals 1200, jaiStream.height
    }

    void testGetAsJaiStreamFirstThenAsByteArrayJpg() {
        ImageFile image = ImageFileFactory.produce(getMultipartFile('image.jpg'))
        RenderedOp jaiStream = image.getAsJaiStream()
        assertEquals 1920, jaiStream.width
        assertEquals 1200, jaiStream.height
        byte[] byteArray = image.getAsByteArray()
        assertEquals 514893, byteArray.size()
        jaiStream = image.getAsJaiStream()
        assertEquals 1920, jaiStream.width
        assertEquals 1200, jaiStream.height
        byteArray = image.getAsByteArray()
        assertEquals 514893, byteArray.size()
    }

    void testGetAsByteArrayFirstThenAsJaiStreamPng() {
        ImageFile image = ImageFileFactory.produce(getMultipartFile('image.png'))
        byte[] byteArray = image.getAsByteArray()
        assertEquals 1511812, byteArray.size()
        RenderedOp jaiStream = image.getAsJaiStream()
        assertEquals 1280, jaiStream.width
        assertEquals 800, jaiStream.height
        byteArray = image.getAsByteArray()
        assertEquals 1511812, byteArray.size()
        jaiStream = image.getAsJaiStream()
        assertEquals 1280, jaiStream.width
        assertEquals 800, jaiStream.height
    }

    void testGetAsJaiStreamFirstThenAsByteArrayPng() {
        ImageFile image = ImageFileFactory.produce(getMultipartFile('image.png'))
        RenderedOp jaiStream = image.getAsJaiStream()
        assertEquals 1280, jaiStream.width
        assertEquals 800, jaiStream.height
        byte[] byteArray = image.getAsByteArray()
        assertEquals 1511812, byteArray.size()
        jaiStream = image.getAsJaiStream()
        assertEquals 1280, jaiStream.width
        assertEquals 800, jaiStream.height
        byteArray = image.getAsByteArray()
        assertEquals 1511812, byteArray.size()
    }

    void testGetAsJaiStreamFirstThenAsByteArrayGif() {
        ImageFile image = ImageFileFactory.produce(getMultipartFile('image.gif'))
        RenderedOp jaiStream = image.getAsJaiStream()
        assertEquals 650, jaiStream.width
        assertEquals 487, jaiStream.height
        byte[] byteArray = image.getAsByteArray()
        assertEquals 186264, byteArray.size()
        jaiStream = image.getAsJaiStream()
        assertEquals 650, jaiStream.width
        assertEquals 487, jaiStream.height
        byteArray = image.getAsByteArray()
        assertEquals 186264, byteArray.size()
    }

    void testGetAsByteArrayFirstThenAsJaiStreamBmp() {
        ImageFile image = ImageFileFactory.produce(getMultipartFile('image.bmp'))
        byte[] byteArray = image.getAsByteArray()
        assertEquals 1254214, byteArray.size()
        RenderedOp jaiStream = image.getAsJaiStream()
        assertEquals 685, jaiStream.width
        assertEquals 610, jaiStream.height
        byteArray = image.getAsByteArray()
        assertEquals 1254214, byteArray.size()
        jaiStream = image.getAsJaiStream()
        assertEquals 685, jaiStream.width
        assertEquals 610, jaiStream.height
    }

    void testGetAsJaiStreamFirstThenAsByteArrayBmp() {
        ImageFile image = ImageFileFactory.produce(getMultipartFile('image.bmp'))
        RenderedOp jaiStream = image.getAsJaiStream()
        assertEquals 685, jaiStream.width
        assertEquals 610, jaiStream.height
        byte[] byteArray = image.getAsByteArray()
        assertEquals 1254214, byteArray.size()
        jaiStream = image.getAsJaiStream()
        assertEquals 685, jaiStream.width
        assertEquals 610, jaiStream.height
        byteArray = image.getAsByteArray()
        assertEquals 1254214, byteArray.size()
    }

    void testPlayWithApiMultipartFile() {
        ImageFile image = ImageFileFactory.produce(getMultipartFile('image.jpg'))
        // get size
        def size = image.getSize()
        assertEquals 1920, size.width
        assertEquals 1200, size.height
        // get as JAI RenderingOps
        RenderedOp jaiStream = image.getAsJaiStream()
        assertEquals 1920, jaiStream.width
        assertEquals 1200, jaiStream.height
        // get as byte array
        byte[] byteArray = image.getAsByteArray()
        assertEquals 514893, byteArray.size()
        // other
        assertEquals 'jpg', image.getExtension()
        assertEquals 'JPEG', image.getEncoder()
        // mix methods to check if java.lang.IllegalArgumentException: im == null! i eliminated
        jaiStream = image.getAsJaiStream()
        assertEquals 1920, jaiStream.width
        assertEquals 1200, jaiStream.height
        size = image.getSize()
        assertEquals 1920, size.width
        assertEquals 1200, size.height
        jaiStream = image.getAsJaiStream()
        assertEquals 1920, jaiStream.width
        assertEquals 1200, jaiStream.height
        size = image.getSize()
        assertEquals 1920, size.width
        assertEquals 1200, size.height
    }
}