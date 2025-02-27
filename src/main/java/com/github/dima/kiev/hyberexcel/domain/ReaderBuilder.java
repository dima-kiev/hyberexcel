package com.github.dima.kiev.hyberexcel.domain;

import com.github.dima.kiev.hyberexcel.exceptions.APIMissUseException;
import com.github.dima.kiev.hyberexcel.impl.meta.SheetMetaInfo;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReaderBuilder {

        private Class<?> entityClass;
        private Path fromFile;
        private InputStream inputStream;
        private String password;
        private FileType fileType;

        public ReaderBuilder forEntity(Class<?> entityClass) {
            this.entityClass = entityClass;
            return  this;
        }

        public ReaderBuilder fromFile(String filePath) {
            this.fromFile = Paths.get(filePath);
            return  this;
        }

        public ReaderBuilder fromFile(Path fromFile) {
            this.fromFile = fromFile;
            return  this;
        }

        public ReaderBuilder fromInputStream(InputStream is) {
            this.inputStream = is;
            return  this;
        }

        public ReaderBuilder withPassword(String password) {
            this.password = password;
            return  this;
        }

        public ReaderBuilder forFileType(FileType fileType) {
            this.fileType = fileType;
            return  this;
        }

        public RReader build() {
            RReader reader = null;



            return  reader;
        }

        private SheetMetaInfo parseEntity() {
            if (entityClass != null) {
                return new SheetMetaInfo(this.entityClass);
            } else {
                throw new APIMissUseException("Entity class is required to build reader");
            }
        }

        private boolean isInputSourceValid() {
            return fromFile != null || inputStream != null;
        }

}
