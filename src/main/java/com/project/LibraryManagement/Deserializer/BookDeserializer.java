//package com.project.LibraryManagement.Deserializer;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import com.project.LibraryManagement.Entity.Book;
//import com.project.LibraryManagement.Repository.AuthorRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//
//public class BookDeserializer extends StdDeserializer<Book> {
//
//    @Autowired
//    AuthorRepository authorRepository;
//
//    protected BookDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    protected BookDeserializer(JavaType valueType) {
//        super(valueType);
//    }
//
//    protected BookDeserializer(StdDeserializer<?> src) {
//        super(src);
//    }
//
//    @Override
//    public Book deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
//
//        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
//        Book book = new Book();
//
//        book.setISBN((Long) jsonNode.get("ISBN").numberValue());
//        book.setTitle(jsonNode.get("title").textValue());
//        book.setSubject(jsonNode.get("subject").textValue());
//        book.setPublisher(jsonNode.get("publisher").textValue());
//        book.setLanguage(jsonNode.get("language").textValue());
//        book.setNumberOfPages((Integer) jsonNode.get("numberOfPages").numberValue());
//
//        Long authorId = (Long) jsonNode.get("author").numberValue();
//        book.setAuthor(authorRepository.findById(authorId).orElse(null));
//
//        return book;
//    }
//}
