package com.example.a15151.processing;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;


import com.example.a15151.entitys.Question;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    private LevelLoader() {
    }

    public static List<Question> loadLevel(Context context, int levelResourceId) {
        List<Question> levelQuestions = new ArrayList<>();
        Resources res = context.getResources();

        try (XmlResourceParser xmlParser = res.getXml(levelResourceId)) {
            XmlPullParser parser = initializeParser(xmlParser);
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("question")) {
                    Question question = parseQuestion(parser);
                    levelQuestions.add(question);
                }
                parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return levelQuestions;
    }

    private static XmlPullParser initializeParser(XmlResourceParser xmlParser) throws XmlPullParserException, IOException {
        xmlParser.next();
        return xmlParser;
    }

    private static Question parseQuestion(XmlPullParser parser) throws XmlPullParserException, IOException {
        String questionText = "";
        List<String> options = new ArrayList<>();
        int correctOptionIndex = -1;

        while (!(parser.getEventType() == XmlPullParser.END_TAG && parser.getName().equals("question"))) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                String tagName = parser.getName();
                switch (tagName) {
                    case "text":
                        questionText = parseText(parser);
                        break;
                    case "options":
                        options = parseOptions(parser);
                        break;
                    case "correctOptionIndex":
                        correctOptionIndex = parseCorrectOptionIndex(parser);
                        break;
                }
            }
            parser.next();
        }

        return new Question(questionText, options, correctOptionIndex);
    }

    private static String parseText(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.next();
        return parser.getText();
    }

    private static List<String> parseOptions(XmlPullParser parser) throws IOException, XmlPullParserException {
        List<String> options = new ArrayList<>();
        parser.next();

        while (!(parser.getEventType() == XmlPullParser.END_TAG && parser.getName().equals("options"))) {
            if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("option")) {
                options.add(parseText(parser));
            }
            parser.next();
        }

        return options;
    }

    private static int parseCorrectOptionIndex(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.next();
        return Integer.parseInt(parser.getText());
    }
}

