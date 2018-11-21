package org.michaelbel.moviemade.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

@SuppressWarnings("all")
public class SpannableUtil {

    public static final int FLAG_TAG_BR = 1;
    public static final int FLAG_TAG_BOLD = 2;
    public static final int FLAG_TAG_COLOR = 4;
    public static final int FLAG_TAG_URL = 8;
    public static final int FLAG_TAG_ALL = FLAG_TAG_BR | FLAG_TAG_BOLD | FLAG_TAG_URL;
    public static final int FLAG_TAG_BOLD_COLORS = FLAG_TAG_BOLD | FLAG_TAG_COLOR;

    public static SpannableStringBuilder replaceTags(String str) {
        return replaceTags(str, FLAG_TAG_ALL);
    }

    public static SpannableStringBuilder replaceTags(String str, int flag, Object... args) {
        try {
            int start;
            int end;

            StringBuilder stringBuilder = new StringBuilder(str);

            if ((flag & FLAG_TAG_BR) != 0) {
                while ((start = stringBuilder.indexOf("<br>")) != -1) {
                    stringBuilder.replace(start, start + 4, "\n");
                }

                while ((start = stringBuilder.indexOf("<br/>")) != -1) {
                    stringBuilder.replace(start, start + 5, "\n");
                }
            }

            ArrayList<Integer> bolds = new ArrayList<>();

            if ((flag & FLAG_TAG_BOLD) != 0) {
                while ((start = stringBuilder.indexOf("<b>")) != -1) {
                    stringBuilder.replace(start, start + 3, "");
                    end = stringBuilder.indexOf("</b>");

                    if (end == -1) {
                        end = stringBuilder.indexOf("<b>");
                    }

                    stringBuilder.replace(end, end + 4, "");
                    bolds.add(start);
                    bolds.add(end);
                }

                while ((start = stringBuilder.indexOf("**")) != -1) {
                    stringBuilder.replace(start, start + 2, "");
                    end = stringBuilder.indexOf("**");

                    if (end >= 0) {
                        stringBuilder.replace(end, end + 2, "");
                        bolds.add(start);
                        bolds.add(end);
                    }
                }
            }

            if ((flag & FLAG_TAG_URL) != 0) {
                while ((start = stringBuilder.indexOf("**")) != -1) {
                    stringBuilder.replace(start, start + 2, "");
                    end = stringBuilder.indexOf("**");

                    if (end >= 0) {
                        stringBuilder.replace(end, end + 2, "");
                        bolds.add(start);
                        bolds.add(end);
                    }
                }
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuilder);

            for (int a = 0; a < bolds.size() / 2; a++) {
                spannableStringBuilder.setSpan(new TypefaceSpan("sans-serif-medium"), bolds.get(a * 2), bolds.get(a * 2 + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            return spannableStringBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SpannableStringBuilder(str);
    }

    public static SpannableStringBuilder replaceTags2(String str, int flag, Object... args) {
        try {
            int start;
            int end;

            StringBuilder stringBuilder = new StringBuilder(str);
            ArrayList<Integer> bolds = new ArrayList<>();

            if ((flag & FLAG_TAG_BOLD) != 0) {
                while ((start = stringBuilder.indexOf("<b>")) != -1) {
                    stringBuilder.replace(start, start + 3, "");
                    end = stringBuilder.indexOf("</b>");

                    if (end == -1) {
                        end = stringBuilder.indexOf("<b>");
                    }

                    stringBuilder.replace(end, end + 4, "");
                    bolds.add(start);
                    bolds.add(end);
                }

                while ((start = stringBuilder.indexOf("**")) != -1) {
                    stringBuilder.replace(start, start + 2, "");
                    end = stringBuilder.indexOf("**");

                    if (end >= 0) {
                        stringBuilder.replace(end, end + 2, "");
                        bolds.add(start);
                        bolds.add(end);
                    }
                }
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuilder);

            for (int a = 0; a < bolds.size() / 2; a++) {
                spannableStringBuilder.setSpan(new TypefaceSpan("sans-serif-medium"), bolds.get(a * 2), bolds.get(a * 2 + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Moviemade.AppContext, R.color.primaryText)), bolds.get(a * 2), bolds.get(a * 2 + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            return spannableStringBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SpannableStringBuilder(str);
    }

    public static SpannableStringBuilder boldAndColoredText(String title, String allText) {
        SpannableStringBuilder spannable;

        int startPos = 0;
        int endPos = title.length() - 3; // without ' %s' chars.

        spannable = new SpannableStringBuilder(allText);
        spannable.setSpan(new TypefaceSpan(FontsKt.ROBOTO_MEDIUM), startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Moviemade.AppContext, R.color.primaryText)), startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }

    public static SpannableStringBuilder boldText(String text, String allText) {
        SpannableStringBuilder spannable;

        int startPos = text.length() - 2;
        int endPos = allText.length();

        spannable = new SpannableStringBuilder(allText);
        spannable.setSpan(new TypefaceSpan(FontsKt.ROBOTO_MEDIUM), startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }

    /*public static SpannableStringBuilder linkText(String text) {
        SpannableStringBuilder spannable;

        int startPos = 0;
        int endPos = text.length();

        spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new TypefaceSpan(FontsKt.ROBOTO_MEDIUM), startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Moviemade.AppContext, R.color.tmdbPrimary)), startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }*/
}