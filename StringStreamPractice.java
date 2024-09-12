import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;


class BlogPost {
    String title;
    String author;
    BlogPostType type;
    int likes;
    /**
     * AuthPostTypesLikes
     */
    public record AuthPostTypesLikes(String title, BlogPostType type) {
    }

    BlogPost(String title, BlogPostType type) {
        this(title, type, "default");
    }
    
    BlogPost(String title, BlogPostType type, String author) {
        this.title = title;
        this.type = type;
        this.author = author;
    }

    public BlogPostType getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} by {1}", this.title, this.author);
    }
}

enum BlogPostType {
    NEWS,
    REVIEW,
    GUIDE
}

public class StringStreamPractice {
    public static void main(String[] args) {
        String word = "aabbcccca";

        var counts = word.chars().mapToObj(ch -> (char)ch).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        counts.entrySet().forEach(System.out::println);

        var vals = counts.values().stream().sorted().toList();
        vals.forEach(System.out::println);

        var bp1 = new BlogPost("News1", BlogPostType.NEWS);
        var bp2 = new BlogPost("News2", BlogPostType.NEWS, "Suren");
        var bp3 = new BlogPost("News3", BlogPostType.NEWS, "Suren");
        var bp4 = new BlogPost("Review1", BlogPostType.REVIEW, "Suren");
        var bp5 = new BlogPost("Guide1", BlogPostType.GUIDE, "Bee");
        var bp6 = new BlogPost("Guide2", BlogPostType.GUIDE);

        var posts = Arrays.asList(bp1, bp2, bp3, bp4, bp5, bp6);

        var grouped = posts.stream().collect(Collectors.groupingBy(BlogPost::getType, Collectors.toSet()));

        grouped.entrySet().forEach(System.out::println);
    }
}
