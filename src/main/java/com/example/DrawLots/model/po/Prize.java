package com.example.DrawLots.model.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prize {
    private String type;
    private String name;
    private int number;
    private String picture;
    private String description;
}
