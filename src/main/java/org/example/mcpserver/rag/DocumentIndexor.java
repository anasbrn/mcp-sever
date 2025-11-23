package org.example.mcpserver.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Component
public class DocumentIndexor {
    @Value("classpath:/pdfs/test_rag_dataset.pdf")
    private Resource pdfFileResource;
    @Value("store.json")
    private String storeFile;

    @Bean
    public SimpleVectorStore vectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();

        Path storePath = Path.of("src", "main", "resources", "store");
        File file = new File(storePath.toFile(), storeFile);
        if (!file.exists()) {
            PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(pdfFileResource);
            List<Document> pdfDocuments = pagePdfDocumentReader.get();
            TextSplitter splitter = TokenTextSplitter
                    .builder()
                    .withChunkSize(200)
                    .build();
            List<Document> chunks = splitter.apply(pdfDocuments);
            vectorStore.add(chunks);
            vectorStore.save(file);
        } else {
            vectorStore.load(file);
        }

        return vectorStore;
    }
}
