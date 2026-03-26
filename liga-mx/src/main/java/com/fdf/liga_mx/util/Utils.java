package com.fdf.liga_mx.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

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

                throw new IllegalArgumentException( "Formato invalido en parámetro de ordenamiento. Se espera 'campo,direccion;'" );

            }

            String campo=parts[0];

            Sort.Direction dir = parts.length>1 && parts[1].equalsIgnoreCase("desc")? Sort.Direction.DESC : Sort.Direction.ASC;

            orders.add(new Sort.Order(dir, campo));

        }

        sort=Sort.by(orders);

        return sort;
    }

}
