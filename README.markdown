BurningImage is Grails plugin for image manipulation and image upload handling. Current version allows:

 * use of JAI or ImageMagick rendering engines
 * scale image with approximate width and height
 * scale image with accurate width and height
 * add image watermark
 * crop image
 * write text on image
 * mark domain class as file image container by using @FileImageContainer annotation
 * mark domain class as DB image container by using @DBImageContainer annotation
 * save image binded to domain class by using !ImageUpladService
 * validate uploaded image

It allows to update files stored on server and uploaded as a MultipartFile. Operations could be chained and executed in random order.