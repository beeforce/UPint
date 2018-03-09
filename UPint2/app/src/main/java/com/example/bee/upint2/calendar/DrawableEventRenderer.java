package com.example.bee.upint2.calendar;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bee.upint2.R;
import com.github.tibolte.agendacalendarview.render.EventRenderer;

/**
 * Created by Bee on 3/6/2018.
 */

public class DrawableEventRenderer extends EventRenderer<DrawableCalendarEvent> {
    @Override
    public void render(View view, DrawableCalendarEvent event) {

        ImageView imageView = (ImageView) view.findViewById(R.id.view_agenda_event_image);
        TextView txtTitle = (TextView) view.findViewById(R.id.view_agenda_event_title);
        TextView txtLocation = (TextView) view.findViewById(R.id.view_agenda_event_location);
        TextView txtdescription = (TextView) view.findViewById(R.id.view_agenda_event_description);
        RelativeLayout descriptionContainer = (RelativeLayout) view.findViewById(R.id.view_agenda_event_description_container);
        RelativeLayout locationContainer = (RelativeLayout) view.findViewById(R.id.view_agenda_event_location_container);

        descriptionContainer.setVisibility(View.VISIBLE);

        imageView.setImageDrawable(view.getContext().getResources().getDrawable(event.getDrawableId()));

        txtTitle.setTextColor(view.getResources().getColor(android.R.color.black));

        txtTitle.setText(event.getTitle());
        txtLocation.setText(event.getLocation());
        txtdescription.setText(event.getDescription());
        if (event.getLocation().length() > 0) {
            locationContainer.setVisibility(View.VISIBLE);
            txtLocation.setText(event.getLocation());
            txtdescription.setText(event.getDescription());
        } else {
            locationContainer.setVisibility(View.GONE);
        }

        if (event.getTitle().equals(view.getResources().getString(com.github.tibolte.agendacalendarview.R.string.agenda_event_no_events))) {
            txtTitle.setTextColor(view.getResources().getColor(android.R.color.black));
        } else {
            txtTitle.setTextColor(view.getResources().getColor(com.github.tibolte.agendacalendarview.R.color.theme_text_icons));
        }
        descriptionContainer.setBackgroundColor(event.getColor());
        txtLocation.setTextColor(view.getResources().getColor(com.github.tibolte.agendacalendarview.R.color.theme_text_icons));
    }

    @Override
    public int getEventLayout() {
        return R.layout.view_agenda_drawable_event;
    }

    @Override
    public Class<DrawableCalendarEvent> getRenderType() {
        return DrawableCalendarEvent.class;
    }
}
