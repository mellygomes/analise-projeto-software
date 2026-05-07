package com.jello.jello_app.event;

import com.jello.jello_app.enumeration.EventType;
import com.jello.jello_app.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {
    private User user;
    private EventType type;
    private Map<?, ?> data;
}