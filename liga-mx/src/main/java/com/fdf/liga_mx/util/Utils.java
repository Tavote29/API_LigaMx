package com.fdf.liga_mx.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Utils {

    public Sort parseSortParams(String sorts) {

        String[] arraySorts=sorts.split(";");

        List<Sort.Order> orders= new ArrayList<>();


        Sort sort = Sort.unsorted();

        if (arraySorts.length==0) {
            return sort;
        }

        for (int i = 0; i < arraySorts.length; i++) {

            String[] parts = arraySorts[i].split(",");

            if (parts.length != 2) {

                throw new IllegalArgumentException( "error.utils.sort_invalido" );

            }

            String campo=parts[0];

            Sort.Direction dir = parts.length>1 && parts[1].equalsIgnoreCase("desc")? Sort.Direction.DESC : Sort.Direction.ASC;

            orders.add(new Sort.Order(dir, campo));

        }

        sort=Sort.by(orders);

        return sort;
    }

    public boolean isValidImage(MultipartFile file) {
        try {
            if (!isImage(file)) {
                return false;
            }

            if (!hasImageExtension(file)) {
                return false;
            }

            if (file.getSize() > 3 * 1024 * 1024) {
                return false;
            }

            BufferedImage image = ImageIO.read(file.getInputStream());
            return image != null;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean hasImageExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return filename != null && filename.matches("(?i).*\\.(png|jpg|jpeg|gif|bmp)$");
    }

    public boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }


}
