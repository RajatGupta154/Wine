package com.evozon.training.service;

import com.evozon.training.model.Product;
import com.evozon.training.persistence.ProductPersistence;
import com.evozon.training.rest.RestPersistence;
import com.evozon.training.rest.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractMap;
import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private static final int DEFAULT_CATEGORY_ID = 1;
    private static final int DEFAULT_STOCK = 0;
    @Autowired
    private ProductPersistence productPersistence;
    @Autowired
    private RestPersistence restPersistence;
    @Autowired
    private FileSystemStorageService fileStorage;

    public List<Product> getAllProducts() {
        return productPersistence.getAllProducts();
    }

    public void addProduct(Product product) {
        //todo: transform Enum to string

        productPersistence.addProduct(product);
    }

    public AbstractMap.SimpleEntry<Double, List<Review>> getReviews(Integer id) {
        Double average = (double) 0;
        int numberOfReviews = 0;
        List<Review> list = restPersistence.getReviews(id);
        for (Review review : list) {
            average += review.getRating();
            numberOfReviews++;
        }
        if (average > 0 && numberOfReviews > 0) {
            average /= numberOfReviews;
            average = Math.round(average * 10.0) / 10.0;
            return new AbstractMap.SimpleEntry(average, list);
        } else {
            return new AbstractMap.SimpleEntry(0, list);
        }

    }

    public Product getProduct(Integer id) {
        return productPersistence.getProduct(id);
    }

    public void updateProduct(Product product) {
        productPersistence.update(product);
    }

    public void deleteProduct(String id) {
        Product product = getProduct(Integer.valueOf(id));
        productPersistence.deleteProduct(product);
    }

    public String getProductPicture(int id) {
        return productPersistence.getProductPicture(id);
    }

    public File exportProducts(String listOfids) throws URISyntaxException {
        String[] productIds;
        productIds = listOfids.split(",");
        String fileName = "exportedProducts.csv";
        File file = null;
        try {
            String filePath = getClass().getClassLoader().getResource("../../resources").toString();
            String filePathURI = new URI(filePath).getPath();
            file = new File(filePathURI + fileName);
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            for (int i = 0; i < productIds.length; i++) {
                Product p = getProduct(Integer.parseInt(productIds[i]));
                fileWriter.append(p.getName());
                fileWriter.append(",");
                fileWriter.append(p.getBrand());
                fileWriter.append(",");
                fileWriter.append(p.getWineType());
                fileWriter.append(",");
                fileWriter.append(p.getCountryOfOrigin());
                fileWriter.append("," + p.getYearOfProduction());
                fileWriter.append("," + p.getVolume());
                fileWriter.append("," + p.getPrice());
                fileWriter.append("," + p.getStock());
                if (p.getDescription() != null) {
                    fileWriter.append("," + p.getDescription());
                } else {
                    fileWriter.append(", ");
                }
                if (p.getRecommendations() != null) {
                    fileWriter.append("," + p.getRecommendations());
                } else {
                    fileWriter.append(", ");
                }
                if (p.getPicture() != null) {
                    fileWriter.append("," + p.getPicture());
                } else {
                    fileWriter.append(", ");
                }
                if (i != productIds.length - 1)
                    fileWriter.append("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public int manageImports(String fileData) {
        int nbOfImportedProducts = 0;
        String[] lines = fileData.split("\n");
        for (int i = 0; i < lines.length; i++) {
            boolean valid = true;
            String[] myLine = lines[i].split(",");
            Product newP = new Product();
            for (int j = 0; j < myLine.length; j++) {
                if (!myLine[j].equals("")) {
                    setProductInfo(newP, j, myLine[j]);
                } else {
                    if (j <= 6) {
                        valid = false;
                        break;
                    }
                }
            }
            if (valid && myLine.length > 6) {
                logger.info(newP.toString());
                if (newP.getCategoryID() == null) {
                    newP.setCategoryID(DEFAULT_CATEGORY_ID);
                }
                if (newP.getStock() == null) {
                    newP.setStock(DEFAULT_STOCK);
                }
                try {
                    String fileName = fileStorage.storeDefault();
                    newP.setPicture(fileName);
                } catch (IOException exp) {
                    logger.debug("pictureExceptionDefault: The default picture could not be added also!");
                }
                productPersistence.addProduct(newP);
                nbOfImportedProducts++;
            }
        }
        return nbOfImportedProducts;
    }

    private void setProductInfo(Product newP, int j, String info) {
        switch (j) {
            case 0:
                newP.setName(info);
                break;
            case 1:
                newP.setBrand(info);
                break;
            case 2:
                newP.setWineType(info);
                break;
            case 3:
                newP.setCountryOfOrigin(info);
                break;
            case 4:
                newP.setYearOfProduction(Integer.parseInt(info));
                break;
            case 5:
                newP.setVolume(Double.parseDouble(info));
                break;
            case 6:
                newP.setPrice(Double.parseDouble(info));
                break;
            case 7:
                newP.setStock(Integer.parseInt(info));
                break;
            case 8:
                newP.setDescription(info);
                break;
            case 9:
                newP.setRecommendations(info);
                break;
        }
    }

    public List<Product> sortProducts(String sortingCriteria, Integer startIndex, Integer maxNb) {
        return productPersistence.sortProducts(sortingCriteria, startIndex, maxNb);
    }

    public void deleteAll(String products) {
        productPersistence.deleteAll(products);
    }
}
