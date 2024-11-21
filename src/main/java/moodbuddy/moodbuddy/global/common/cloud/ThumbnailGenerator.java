package moodbuddy.moodbuddy.global.common.cloud;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ThumbnailGenerator {
    public void resizeImage(
            File file,
            File resizeFile,
            String fileName,
            String fileExtension
    ) throws IOException {
        if (fileName == null || fileName.isBlank()) {
            fileName = file.getPath();
        }
        if (file.length() >= 1024 * 1024) {
            resizeImage(file, fileName, fileExtension, resizeFile);
        } else {
            ImmutableImage image = ImmutableImage.loader().fromFile(file);
            image.output(WebpWriter.DEFAULT.withQ(50), resizeFile);
        }
    }

    private void resizeImage(
            File file,
            String fileName,
            String fileExtension,
            File resizeFile
    ) throws IOException {
        var resizeTempFile = File.createTempFile(FilenameUtils.getPath(fileName) + FilenameUtils.getBaseName(fileName) + "_thumb__", fileExtension);
        try {
            resizeImageWithChunk(
                    file, resizeTempFile, fileExtension
            );
            ImmutableImage image = ImmutableImage.loader().fromFile(resizeTempFile);
            image.output(WebpWriter.DEFAULT.withQ(50), resizeFile);
        } finally {
            resizeTempFile.delete();
        }
    }

    private void resizeImageWithChunk(
            File file,
            File resizeFile,
            String fileExtension
    ) throws IOException {
        if (fileExtension == null || fileExtension.isBlank()) {
            fileExtension = FilenameUtils.getExtension(file.getName());
        }
        ImageInfo imageInfo = Imaging.getImageInfo(file);
        int width = imageInfo.getWidth();
        int height = imageInfo.getHeight();

        long chunkLength = file.length() / (1024 * 512);
        long chunkHeight = height / chunkLength;
        var startHeight = 0L;

        BufferedImage image = new BufferedImage(
                (int) (width * .3),
                (int) (height * .3),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics graphics = image.getGraphics();
        long y = 0L;

        for (long index = 0; index <= chunkLength; index++) {
            File chunkFile = new File(UUID.randomUUID() + "_chunk_" + index + "." + fileExtension);
            try {
                long chunkHeightSize = (index == chunkLength) ? height - (chunkLength * chunkHeight) : chunkHeight;
                if (chunkHeightSize <= 0) continue;
                Thumbnails.of(file)
                        .sourceRegion(0, (int) startHeight, width, (int) chunkHeightSize)
                        .outputQuality(.5)
                        .scale(.3)
                        .outputFormat(fileExtension)
                        .toFile(chunkFile);

                startHeight += chunkHeight;
                y = drawChunkImage(graphics, y, chunkFile);
            } finally {
                chunkFile.delete();
            }
        }

        graphics.dispose();
        ImageIO.write(image, fileExtension, resizeFile);
    }

    private long drawChunkImage(Graphics graphics, Long y, File chunkFile) throws IOException {
        BufferedImage chunkImage = ImageIO.read(chunkFile);
        graphics.drawImage(chunkImage, 0, y.intValue(), null);
        return y + chunkImage.getHeight();
    }
}
