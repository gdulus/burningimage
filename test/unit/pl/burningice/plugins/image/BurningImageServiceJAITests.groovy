package pl.burningice.plugins.image

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.junit.Before
import pl.burningice.plugins.image.engines.Worker
import pl.burningice.plugins.image.test.UnitTestFileUploadUtils

import java.awt.*

/**
 * @author pawel.gdula@burningice.pl
 */
@TestMixin([UnitTestFileUploadUtils, GrailsUnitTestMixin])
class BurningImageServiceJAITests {

    private def burningImageService

    @Before
    public void setUp() throws Exception {
        cleanUpTestDir()
        burningImageService = new BurningImageService()
    }

    void testBaseSetupLocalFile() {
        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(null, null)
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith('not/existing/file', null)
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), null)
        }

        shouldFail(FileNotFoundException) {
            burningImageService.doWith(getFilePath('image.jpg'), 'not/exists/dir')
        }

        shouldFail(FileNotFoundException) {
            burningImageService.doWith('not/existing/file', resultDir)
        }

        assertTrue burningImageService.doWith(getFilePath('image.jpg'), resultDir) instanceof Worker
    }

    void testBaseSetupMultipart() {
        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(null, null)
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getEmptyMultipartFile(), null)
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getMultipartFile('image.jpg'), null)
        }

        shouldFail(FileNotFoundException) {
            burningImageService.doWith(getMultipartFile('image.jpg'), 'not/exists/dir')
        }

        shouldFail(FileNotFoundException) {
            burningImageService.doWith(getEmptyMultipartFile(), resultDir)
        }

        assertTrue burningImageService.doWith(getMultipartFile('image.jpg'), resultDir) instanceof Worker
    }

    void testScaleApproximateMultipartFile() {
        assertFalse fileExists('image.jpg')
        def scaleResult, result, file

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute {
            scaleResult = it.scaleApproximate(50, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute {
            scaleResult = it.scaleApproximate(50, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 2000

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute {
            scaleResult = it.scaleApproximate(2000, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute {
            scaleResult = it.scaleApproximate(2000, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 2000
    }

    void testScaleApproximateLocalFileJpg() {
        assertFalse fileExists('image.jpg')
        def scaleResult, result, file

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
            scaleResult = it.scaleApproximate(50, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
            scaleResult = it.scaleApproximate(50, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 2000

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
            scaleResult = it.scaleApproximate(2000, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
            scaleResult = it.scaleApproximate(2000, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 2000
    }

    void testScaleAccurateLocalFileJpgBig() {
        assertFalse fileExists('image2.jpg')
        def file

        burningImageService.doWith(getFilePath('image2.jpg'), resultDir).execute {
            it.scaleAccurate(178, 178)
        }

        assertTrue fileExists('image2.jpg')
        file = getFile('image2.jpg')
        assertTrue file.width == 178
        assertTrue file.height == 178

        cleanUpTestDir()
        assertFalse fileExists('image2.jpg')

        burningImageService.doWith(getFilePath('image2.jpg'), resultDir).execute {
            it.scaleAccurate(51, 62)
        }

        assertTrue fileExists('image2.jpg')
        file = getFile('image2.jpg')
        assertTrue file.width == 51
        assertTrue file.height == 62
    }

    void testScaleAccurateLocalFileJpg() {
        assertFalse fileExists('image.jpg')
        def scaleResult, result, file

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
            scaleResult = it.scaleAccurate(50, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 50
        assertTrue file.height == 50

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
            scaleResult = it.scaleAccurate(50, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 50
        assertTrue file.height == 2000

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
            scaleResult = it.scaleAccurate(2000, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 2000
        assertTrue file.height == 50

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
            scaleResult = it.scaleAccurate(2000, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 2000
        assertTrue file.height == 2000
    }

    void testScaleAccurateMultipartFileJpg() {
        assertFalse fileExists('image.jpg')
        def scaleResult, result, file

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute {
            scaleResult = it.scaleAccurate(50, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 50
        assertTrue file.height == 50

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute {
            scaleResult = it.scaleAccurate(50, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 50
        assertTrue file.height == 2000

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute {
            scaleResult = it.scaleAccurate(2000, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 2000
        assertTrue file.height == 50

        cleanUpTestDir()
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute {
            scaleResult = it.scaleAccurate(2000, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 2000
        assertTrue file.height == 2000
    }

    void testScaleApproximateLocalFileJpgWithName() {
        def scaleResult, result, file

        assertFalse fileExists('jpg-50x50.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute('jpg-50x50', {
            scaleResult = it.scaleApproximate(50, 50)
        })

        assertEquals 'jpg-50x50.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('jpg-50x50.jpg')
        file = getFile('jpg-50x50.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('jpg-50x2000.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute('jpg-50x2000', {
            scaleResult = it.scaleApproximate(50, 2000)
        })
        assertEquals 'jpg-50x2000.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('jpg-50x2000.jpg')
        file = getFile('jpg-50x2000.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 2000

        cleanUpTestDir()
        assertFalse fileExists('jpg-2000x50.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute('jpg-2000x50', {
            scaleResult = it.scaleApproximate(2000, 50)
        })
        assertEquals 'jpg-2000x50.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('jpg-2000x50.jpg')
        file = getFile('jpg-2000x50.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('jpg-2000x2000.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute('jpg-2000x2000', {
            scaleResult = it.scaleApproximate(2000, 2000)
        })
        assertEquals 'jpg-2000x2000.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('jpg-2000x2000.jpg')
        file = getFile('jpg-2000x2000.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 2000
    }

    void testScaleApproximateMultipartFileJpgWithName() {
        def scaleResult, result, file

        assertFalse fileExists('jpg-50x50.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute('jpg-50x50', {
            scaleResult = it.scaleApproximate(50, 50)
        })

        assertEquals 'jpg-50x50.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('jpg-50x50.jpg')
        file = getFile('jpg-50x50.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('jpg-50x2000.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute('jpg-50x2000', {
            scaleResult = it.scaleApproximate(50, 2000)
        })
        assertEquals 'jpg-50x2000.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('jpg-50x2000.jpg')
        file = getFile('jpg-50x2000.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 2000

        cleanUpTestDir()
        assertFalse fileExists('jpg-2000x50.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute('jpg-2000x50', {
            scaleResult = it.scaleApproximate(2000, 50)
        })
        assertEquals 'jpg-2000x50.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('jpg-2000x50.jpg')
        file = getFile('jpg-2000x50.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('jpg-2000x2000.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute('jpg-2000x2000', {
            scaleResult = it.scaleApproximate(2000, 2000)
        })
        assertEquals 'jpg-2000x2000.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('jpg-2000x2000.jpg')
        file = getFile('jpg-2000x2000.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 2000
    }

    void testScaleApproximateLocalFileBmp() {
        assertFalse fileExists('image.bmp')
        def scaleResult, result, file

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute {
            scaleResult = it.scaleApproximate(50, 50)
        }
        assertEquals 'image.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.bmp')
        file = getFile('image.bmp')
        assertTrue file.width <= 50
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('image.bmp')

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute {
            scaleResult = it.scaleApproximate(50, 2000)
        }
        assertEquals 'image.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.bmp')
        file = getFile('image.bmp')
        assertTrue file.width <= 50
        assertTrue file.height <= 2000

        cleanUpTestDir()
        assertFalse fileExists('image.bmp')

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute {
            scaleResult = it.scaleApproximate(2000, 50)
        }
        assertEquals 'image.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.bmp')
        file = getFile('image.bmp')
        assertTrue file.width <= 2000
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('image.bmp')

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute {
            scaleResult = it.scaleApproximate(2000, 2000)
        }
        assertEquals 'image.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.bmp')
        file = getFile('image.bmp')
        assertTrue file.width <= 2000
        assertTrue file.height <= 2000
    }

    void testScaleAccurateLocalFileBmp() {
        assertFalse fileExists('image.bmp')
        def scaleResult, result, file

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute {
            scaleResult = it.scaleAccurate(50, 50)
        }
        assertEquals 'image.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.bmp')
        file = getFile('image.bmp')
        assertTrue file.width == 50
        assertTrue file.height == 50

        cleanUpTestDir()
        assertFalse fileExists('image.bmp')

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute {
            scaleResult = it.scaleAccurate(50, 2000)
        }
        assertEquals 'image.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.bmp')
        file = getFile('image.bmp')
        assertTrue file.width == 50
        assertTrue file.height == 2000

        cleanUpTestDir()
        assertFalse fileExists('image.bmp')

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute {
            scaleResult = it.scaleAccurate(2000, 50)
        }
        assertEquals 'image.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.bmp')
        file = getFile('image.bmp')
        assertTrue file.width == 2000
        assertTrue file.height == 50

        cleanUpTestDir()
        assertFalse fileExists('image.bmp')

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute {
            scaleResult = it.scaleAccurate(2000, 2000)
        }
        assertEquals 'image.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.bmp')
        file = getFile('image.bmp')
        assertTrue file.width == 2000
        assertTrue file.height == 2000
    }

    void testScaleApproximateLocalFileBmpWithName() {
        def scaleResult, result, file

        assertFalse fileExists('bmp-50x50.bmp')

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute('bmp-50x50', {
            scaleResult = it.scaleApproximate(50, 50)
        })

        assertEquals 'bmp-50x50.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('bmp-50x50.bmp')
        file = getFile('bmp-50x50.bmp')
        assertTrue file.width <= 50
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('bmp-50x2000.bmp')

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute('bmp-50x2000', {
            scaleResult = it.scaleApproximate(50, 2000)
        })
        assertEquals 'bmp-50x2000.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('bmp-50x2000.bmp')
        file = getFile('bmp-50x2000.bmp')
        assertTrue file.width <= 50
        assertTrue file.height <= 2000

        cleanUpTestDir()
        assertFalse fileExists('bmp-2000x50.bmp')

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute('bmp-2000x50', {
            scaleResult = it.scaleApproximate(2000, 50)
        })
        assertEquals 'bmp-2000x50.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('bmp-2000x50.bmp')
        file = getFile('bmp-2000x50.bmp')
        assertTrue file.width <= 2000
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('bmp-2000x2000.bmp')

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute('bmp-2000x2000', {
            scaleResult = it.scaleApproximate(2000, 2000)
        })
        assertEquals 'bmp-2000x2000.bmp', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('bmp-2000x2000.bmp')
        file = getFile('bmp-2000x2000.bmp')
        assertTrue file.width <= 2000
        assertTrue file.height <= 2000
    }

    void testScaleApproximateLocalFilePng() {
        assertFalse fileExists('image.png')
        def scaleResult, result, file

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute {
            scaleResult = it.scaleApproximate(50, 50)
        }
        assertEquals 'image.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.png')
        file = getFile('image.png')
        assertTrue file.width <= 50
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('image.png')

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute {
            scaleResult = it.scaleApproximate(50, 2000)
        }
        assertEquals 'image.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.png')
        file = getFile('image.png')
        assertTrue file.width <= 50
        assertTrue file.height <= 2000

        cleanUpTestDir()
        assertFalse fileExists('image.png')

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute {
            scaleResult = it.scaleApproximate(2000, 50)
        }
        assertEquals 'image.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.png')
        file = getFile('image.png')
        assertTrue file.width <= 2000
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('image.png')

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute {
            scaleResult = it.scaleApproximate(2000, 2000)
        }
        assertEquals 'image.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.png')
        file = getFile('image.png')
        assertTrue file.width <= 2000
        assertTrue file.height <= 2000
    }

    void testScaleAccurateLocalFilePng() {
        assertFalse fileExists('image.png')
        def scaleResult, result, file

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute {
            scaleResult = it.scaleAccurate(50, 50)
        }
        assertEquals 'image.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.png')
        file = getFile('image.png')
        assertTrue file.width == 50
        assertTrue file.height == 50

        cleanUpTestDir()
        assertFalse fileExists('image.png')

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute {
            scaleResult = it.scaleAccurate(50, 2000)
        }
        assertEquals 'image.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.png')
        file = getFile('image.png')
        assertTrue file.width == 50
        assertTrue file.height == 2000

        cleanUpTestDir()
        assertFalse fileExists('image.png')

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute {
            scaleResult = it.scaleAccurate(2000, 50)
        }
        assertEquals 'image.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.png')
        file = getFile('image.png')
        assertTrue file.width == 2000
        assertTrue file.height == 50

        cleanUpTestDir()
        assertFalse fileExists('image.png')

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute {
            scaleResult = it.scaleAccurate(2000, 2000)
        }
        assertEquals 'image.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.png')
        file = getFile('image.png')
        assertTrue file.width == 2000
        assertTrue file.height == 2000
    }

    void testScaleApproximateLocalFilePngWithName() {
        def scaleResult, result, file

        assertFalse fileExists('png-50x50.png')

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute('png-50x50', {
            scaleResult = it.scaleApproximate(50, 50)
        })

        assertEquals 'png-50x50.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('png-50x50.png')
        file = getFile('png-50x50.png')
        assertTrue file.width <= 50
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('png-50x2000.png')

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute('png-50x2000', {
            scaleResult = it.scaleApproximate(50, 2000)
        })
        assertEquals 'png-50x2000.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('png-50x2000.png')
        file = getFile('png-50x2000.png')
        assertTrue file.width <= 50
        assertTrue file.height <= 2000

        cleanUpTestDir()
        assertFalse fileExists('png-2000x50.png')

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute('png-2000x50', {
            scaleResult = it.scaleApproximate(2000, 50)
        })
        assertEquals 'png-2000x50.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('png-2000x50.png')
        file = getFile('png-2000x50.png')
        assertTrue file.width <= 2000
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('png-2000x2000.png')

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute('png-2000x2000', {
            scaleResult = it.scaleApproximate(2000, 2000)
        })
        assertEquals 'png-2000x2000.png', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('png-2000x2000.png')
        file = getFile('png-2000x2000.png')
        assertTrue file.width <= 2000
        assertTrue file.height <= 2000
    }

    void testScaleApproximateLocalFileGif() {
        assertFalse fileExists('image.gif')
        assertFalse fileExists('image.jpg')
        def scaleResult, result, file

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
            scaleResult = it.scaleApproximate(50, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('image.gif')
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
            scaleResult = it.scaleApproximate(50, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 2000

        cleanUpTestDir()
        assertFalse fileExists('image.gif')
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
            scaleResult = it.scaleApproximate(2000, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('image.gif')
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
            scaleResult = it.scaleApproximate(2000, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 2000
    }

    void testScaleAccurateLocalFileGif() {
        assertFalse fileExists('image.gif')
        assertFalse fileExists('image.jpg')
        def scaleResult, result, file

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
            scaleResult = it.scaleAccurate(50, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 50
        assertTrue file.height == 50

        cleanUpTestDir()
        assertFalse fileExists('image.gif')
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
            scaleResult = it.scaleAccurate(50, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 50
        assertTrue file.height == 2000

        cleanUpTestDir()
        assertFalse fileExists('image.gif')
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
            scaleResult = it.scaleAccurate(2000, 50)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 2000
        assertTrue file.height == 50

        cleanUpTestDir()
        assertFalse fileExists('image.gif')
        assertFalse fileExists('image.jpg')

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
            scaleResult = it.scaleAccurate(2000, 2000)
        }
        assertEquals 'image.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('image.jpg')
        file = getFile('image.jpg')
        assertTrue file.width == 2000
        assertTrue file.height == 2000
    }

    void testScaleApproximateLocalFileGifWithName() {
        def scaleResult, result, file

        assertFalse fileExists('50x50.gif')
        assertFalse fileExists('50x50.jpg')

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute('50x50', {
            scaleResult = it.scaleApproximate(50, 50)
        })

        assertEquals '50x50.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('50x50.jpg')
        file = getFile('50x50.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('50x2000.gif')
        assertFalse fileExists('50x2000.jpg')

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute('50x2000', {
            scaleResult = it.scaleApproximate(50, 2000)
        })
        assertEquals '50x2000.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('50x2000.jpg')
        file = getFile('50x2000.jpg')
        assertTrue file.width <= 50
        assertTrue file.height <= 2000

        cleanUpTestDir()
        assertFalse fileExists('2000x50.gif')
        assertFalse fileExists('2000x50.jpg')

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute('2000x50', {
            scaleResult = it.scaleApproximate(2000, 50)
        })
        assertEquals '2000x50.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('2000x50.jpg')
        file = getFile('2000x50.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 50

        cleanUpTestDir()
        assertFalse fileExists('2000x2000.gif')
        assertFalse fileExists('2000x2000.jpg')

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute('2000x2000', {
            scaleResult = it.scaleApproximate(2000, 2000)
        })
        assertEquals '2000x2000.jpg', scaleResult
        assertTrue result instanceof Worker
        assertTrue fileExists('2000x2000.jpg')
        file = getFile('2000x2000.jpg')
        assertTrue file.width <= 2000
        assertTrue file.height <= 2000
    }

    void testWatermarkError() {
        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
                it.watermark(null, null)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
                it.watermark('/not/exists', ['left': 10, 'right': 10])
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
                it.watermark('/not/exists', ['top': 10, 'bottom': 10])
            }
        }

        shouldFail(FileNotFoundException) {
            burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
                it.watermark('/not/exists')
            }
        }
    }

    void testWatermarkLocalJpg() {
        def scaleResult, result

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
            scaleResult = it.watermark('./resources/testImages/watermark.png')
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')
    }

    void testWatermarkLocalGif() {
        def scaleResult, result

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
            scaleResult = it.watermark('./resources/testImages/watermark.png')
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')
    }

    void testWatermarkLocalPng() {
        def scaleResult, result

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute {
            scaleResult = it.watermark('./resources/testImages/watermark.png')
        }

        assertTrue result instanceof Worker
        assertEquals 'image.png', scaleResult
        assertTrue fileExists('image.png')
    }

    void testWatermarkLocalBmp() {
        def scaleResult, result

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute {
            scaleResult = it.watermark('./resources/testImages/watermark.png')
        }

        assertTrue result instanceof Worker
        assertEquals 'image.bmp', scaleResult
        assertTrue fileExists('image.bmp')
    }

    void testWatermarkLocalJpgLocation() {
        def scaleResult, result

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute('left-top-watermark', {
            scaleResult = it.watermark('./resources/testImages/watermark.png', ['left': 20, 'top': 20])
        })

        assertTrue result instanceof Worker
        assertEquals 'left-top-watermark.jpg', scaleResult
        assertTrue fileExists('left-top-watermark.jpg')

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute('right-bottom-watermark', {
            scaleResult = it.watermark('./resources/testImages/watermark.png', ['right': 20, 'bottom': 20])
        })

        assertTrue result instanceof Worker
        assertEquals 'right-bottom-watermark.jpg', scaleResult
        assertTrue fileExists('right-bottom-watermark.jpg')
    }

    void testWatermarkRemoteJpgLocation() {
        def scaleResult, result

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute({
            scaleResult = it.watermark('./resources/testImages/watermark.png', ['left': 20, 'top': 20])
        })

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')
    }

    void testChaining() {
        def scaleResult, result

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute('watermark-scale', {
            it.watermark('./resources/testImages/watermark.png')
            scaleResult = it.scaleApproximate(200, 200)
        })

        assertTrue result instanceof Worker
        assertEquals 'watermark-scale.jpg', scaleResult
        assertTrue fileExists('watermark-scale.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute('scale-watermark', {
            scaleResult = it.scaleApproximate(200, 200)
            it.watermark('./resources/testImages/watermark.png')
        })

        assertTrue result instanceof Worker
        assertEquals 'scale-watermark.jpg', scaleResult
        assertTrue fileExists('scale-watermark.jpg')

        result = burningImageService.doWith(getMultipartFile('image.jpg'), resultDir).execute('complex', {
            it.watermark('./resources/testImages/watermark.png', ['left': 20, 'top': 20])
            scaleResult = it.scaleApproximate(100, 50)
            scaleResult = it.scaleAccurate(50, 100)
            it.watermark('./resources/testImages/watermark.png', ['right': 20, 'bottom': 20])
            scaleResult = it.scaleApproximate(50, 100)
            scaleResult = it.scaleAccurate(100, 50)
        })

        assertTrue result instanceof Worker
        assertEquals 'complex.jpg', scaleResult
        assertTrue fileExists('complex.jpg')
    }

    void testCropError() {

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(null, null, null, null)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(10, null, null, null)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(0, 0, null, null)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(0, 0, 10, null)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(-10, 0, 10, 10)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(0, -10, 10, 10)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(0, 0, -10, 10)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(0, 0, 0, -10)
            }
        }

        def image = getFile('image.jpg', sourceDir)

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop((image.width + 10), 0, 10, 10)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(0, (image.height + 10), 10, 10)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(0, 0, (image.width + 10), 10)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(0, 0, 10, (image.height + 10))
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(image.width, 0, 10, 10)
            }
        }

        shouldFail(IllegalArgumentException) {
            burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
                it.crop(0, image.height, 10, 10)
            }
        }
    }

    void testCropJpgLocalFile() {
        def result, scaleResult, image

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute {
            scaleResult = it.crop(0, 0, 50, 100)
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')
        image = getFile('image.jpg')
        assertTrue image.width == 50
        assertTrue image.height == 100
    }

    void testCropBmpLocalFile() {
        def result, scaleResult, image

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute {
            scaleResult = it.crop(20, 30, 50, 40)
        }

        assertTrue result instanceof Worker
        assertEquals 'image.bmp', scaleResult
        assertTrue fileExists('image.bmp')
        image = getFile('image.bmp')
        assertTrue image.width == 50
        assertTrue image.height == 40
    }

    void testCropPngLocalFile() {
        def result, scaleResult, image

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute {
            scaleResult = it.crop(20, 30, 50, 40)
        }

        assertTrue result instanceof Worker
        assertEquals 'image.png', scaleResult
        assertTrue fileExists('image.png')
        image = getFile('image.png')
        assertTrue image.width == 50
        assertTrue image.height == 40
    }

    void testCropGifLocalFile() {
        def result, scaleResult, image

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute {
            scaleResult = it.crop(100, 100, 200, 230)
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')
        image = getFile('image.jpg')
        assertTrue image.width == 200
        assertTrue image.height == 230
    }

    void testTextJpgLocalFile() {
        def result, scaleResult, image

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute { img ->
            scaleResult = img.text({
                it.write("text one", 10, 10)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute { img ->
            scaleResult = img.text(Color.WHITE, {
                it.write("text one", 10, 10)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute { img ->
            scaleResult = img.text(new Font('Arial', Font.PLAIN, 30), {
                it.write("text one", 50, 50)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.jpg'), resultDir).execute { img ->
            scaleResult = img.text(Color.WHITE, new Font('Arial', Font.PLAIN, 30), {
                it.write("text one", 50, 50)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')
    }

    void testTextBmpLocalFile() {
        def result, scaleResult, image

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute { img ->
            scaleResult = img.text({
                it.write("text one", 10, 10)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.bmp', scaleResult
        assertTrue fileExists('image.bmp')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute { img ->
            scaleResult = img.text(Color.WHITE, {
                it.write("text one", 10, 10)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.bmp', scaleResult
        assertTrue fileExists('image.bmp')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute { img ->
            scaleResult = img.text(new Font('Arial', Font.PLAIN, 30), {
                it.write("text one", 50, 50)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.bmp', scaleResult
        assertTrue fileExists('image.bmp')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.bmp'), resultDir).execute { img ->
            scaleResult = img.text(Color.WHITE, new Font('Arial', Font.PLAIN, 30), {
                it.write("text one", 50, 50)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.bmp', scaleResult
        assertTrue fileExists('image.bmp')
    }

    void testTextGifLocalFile() {
        def result, scaleResult, image

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute { img ->
            scaleResult = img.text({
                it.write("text one", 10, 10)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute { img ->
            scaleResult = img.text(Color.WHITE, {
                it.write("text one", 10, 10)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute { img ->
            scaleResult = img.text(new Font('Arial', Font.PLAIN, 30), {
                it.write("text one", 50, 50)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.gif'), resultDir).execute { img ->
            scaleResult = img.text(Color.WHITE, new Font('Arial', Font.PLAIN, 30), {
                it.write("text one", 50, 50)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.jpg', scaleResult
        assertTrue fileExists('image.jpg')
    }

    void testTextPngLocalFile() {
        def result, scaleResult, image

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute { img ->
            scaleResult = img.text({
                it.write("text one", 10, 10)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.png', scaleResult
        assertTrue fileExists('image.png')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute { img ->
            scaleResult = img.text(Color.WHITE, {
                it.write("text one", 10, 10)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.png', scaleResult
        assertTrue fileExists('image.png')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute { img ->
            scaleResult = img.text(new Font('Arial', Font.PLAIN, 30), {
                it.write("text one", 50, 50)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.png', scaleResult
        assertTrue fileExists('image.png')

        cleanUpTestDir()

        result = burningImageService.doWith(getFilePath('image.png'), resultDir).execute { img ->
            scaleResult = img.text(Color.WHITE, new Font('Arial', Font.PLAIN, 30), {
                it.write("text one", 50, 50)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })
        }

        assertTrue result instanceof Worker
        assertEquals 'image.png', scaleResult
        assertTrue fileExists('image.png')
    }

    void testChainExecutions() {
        def result, result1, result2, image

        result = burningImageService.doWith(getFilePath('image.png'), resultDir)
            .execute('first', { img ->
            result1 = img.scaleAccurate(100, 200)
            img.watermark('./resources/testImages/watermark.png', ['top': 10, 'left': 10])
        })
            .execute('second', { img ->
            result2 = img.crop(100, 100, 500, 500)

            img.text({
                it.write("text one", 10, 10)
                it.write("text two", 100, 100)
                it.write("text three", 200, 200)
            })

        })
            .execute('three', { img ->
            img.crop(0, 0, 600, 600)
            img.text({
                it.write("this is file number three", 10, 10)
            })
        })
            .execute('four', { img ->
            img.text({
                it.write("this is file number four", 10, 10)
            })
        })

        assertTrue result instanceof Worker
        assertEquals 'first.png', result1
        assertTrue fileExists('first.png')
        image = getFile('first.png')
        assertTrue image.width == 100
        assertTrue image.height == 200

        assertEquals 'second.png', result2
        assertTrue fileExists('second.png')
        image = getFile('second.png')
        assertTrue image.width == 500
        assertTrue image.height == 500

        assertTrue fileExists('three.png')
        image = getFile('three.png')
        assertTrue image.width == 600
        assertTrue image.height == 600

        assertTrue fileExists('four.png')
        image = getFile('four.png')
        def sourceImage = getFile('image.png', sourceDir)
        assertTrue image.width == sourceImage.width
        assertTrue image.height == sourceImage.height
    }
}
