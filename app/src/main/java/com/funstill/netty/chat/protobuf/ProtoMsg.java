// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ProtoMsg.proto

package com.funstill.netty.chat.protobuf;

public final class ProtoMsg {
  private ProtoMsg() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ContentOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Content)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string uuid = 1;</code>
     */
    String getUuid();
    /**
     * <code>string uuid = 1;</code>
     */
    com.google.protobuf.ByteString
        getUuidBytes();

    /**
     * <code>uint32 protoType = 3;</code>
     */
    int getProtoType();

    /**
     * <code>bytes content = 2;</code>
     */
    com.google.protobuf.ByteString getContent();
  }
  /**
   * Protobuf type {@code Content}
   */
  public  static final class Content extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Content)
      ContentOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Content.newBuilder() to construct.
    private Content(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Content() {
      uuid_ = "";
      protoType_ = 0;
      content_ = com.google.protobuf.ByteString.EMPTY;
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Content(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownFieldProto3(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              String s = input.readStringRequireUtf8();

              uuid_ = s;
              break;
            }
            case 18: {

              content_ = input.readBytes();
              break;
            }
            case 24: {

              protoType_ = input.readUInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ProtoMsg.internal_static_Content_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ProtoMsg.internal_static_Content_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Content.class, Builder.class);
    }

    public static final int UUID_FIELD_NUMBER = 1;
    private volatile Object uuid_;
    /**
     * <code>string uuid = 1;</code>
     */
    public String getUuid() {
      Object ref = uuid_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        uuid_ = s;
        return s;
      }
    }
    /**
     * <code>string uuid = 1;</code>
     */
    public com.google.protobuf.ByteString
        getUuidBytes() {
      Object ref = uuid_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        uuid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PROTOTYPE_FIELD_NUMBER = 3;
    private int protoType_;
    /**
     * <code>uint32 protoType = 3;</code>
     */
    public int getProtoType() {
      return protoType_;
    }

    public static final int CONTENT_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString content_;
    /**
     * <code>bytes content = 2;</code>
     */
    public com.google.protobuf.ByteString getContent() {
      return content_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getUuidBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, uuid_);
      }
      if (!content_.isEmpty()) {
        output.writeBytes(2, content_);
      }
      if (protoType_ != 0) {
        output.writeUInt32(3, protoType_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getUuidBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, uuid_);
      }
      if (!content_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, content_);
      }
      if (protoType_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(3, protoType_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Content)) {
        return super.equals(obj);
      }
      Content other = (Content) obj;

      boolean result = true;
      result = result && getUuid()
          .equals(other.getUuid());
      result = result && (getProtoType()
          == other.getProtoType());
      result = result && getContent()
          .equals(other.getContent());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + UUID_FIELD_NUMBER;
      hash = (53 * hash) + getUuid().hashCode();
      hash = (37 * hash) + PROTOTYPE_FIELD_NUMBER;
      hash = (53 * hash) + getProtoType();
      hash = (37 * hash) + CONTENT_FIELD_NUMBER;
      hash = (53 * hash) + getContent().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Content parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Content parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Content parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Content parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Content parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Content parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Content parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Content parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Content parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Content parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Content parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Content parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Content prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Content}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Content)
        ContentOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ProtoMsg.internal_static_Content_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ProtoMsg.internal_static_Content_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Content.class, Builder.class);
      }

      // Construct using com.funstill.netty.chat.protobuf.ProtoMsg.Content.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        uuid_ = "";

        protoType_ = 0;

        content_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ProtoMsg.internal_static_Content_descriptor;
      }

      public Content getDefaultInstanceForType() {
        return Content.getDefaultInstance();
      }

      public Content build() {
        Content result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public Content buildPartial() {
        Content result = new Content(this);
        result.uuid_ = uuid_;
        result.protoType_ = protoType_;
        result.content_ = content_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Content) {
          return mergeFrom((Content)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Content other) {
        if (other == Content.getDefaultInstance()) return this;
        if (!other.getUuid().isEmpty()) {
          uuid_ = other.uuid_;
          onChanged();
        }
        if (other.getProtoType() != 0) {
          setProtoType(other.getProtoType());
        }
        if (other.getContent() != com.google.protobuf.ByteString.EMPTY) {
          setContent(other.getContent());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Content parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Content) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private Object uuid_ = "";
      /**
       * <code>string uuid = 1;</code>
       */
      public String getUuid() {
        Object ref = uuid_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          uuid_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string uuid = 1;</code>
       */
      public com.google.protobuf.ByteString
          getUuidBytes() {
        Object ref = uuid_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          uuid_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string uuid = 1;</code>
       */
      public Builder setUuid(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        uuid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string uuid = 1;</code>
       */
      public Builder clearUuid() {
        
        uuid_ = getDefaultInstance().getUuid();
        onChanged();
        return this;
      }
      /**
       * <code>string uuid = 1;</code>
       */
      public Builder setUuidBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        uuid_ = value;
        onChanged();
        return this;
      }

      private int protoType_ ;
      /**
       * <code>uint32 protoType = 3;</code>
       */
      public int getProtoType() {
        return protoType_;
      }
      /**
       * <code>uint32 protoType = 3;</code>
       */
      public Builder setProtoType(int value) {
        
        protoType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 protoType = 3;</code>
       */
      public Builder clearProtoType() {
        
        protoType_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString content_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes content = 2;</code>
       */
      public com.google.protobuf.ByteString getContent() {
        return content_;
      }
      /**
       * <code>bytes content = 2;</code>
       */
      public Builder setContent(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        content_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes content = 2;</code>
       */
      public Builder clearContent() {
        
        content_ = getDefaultInstance().getContent();
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFieldsProto3(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:Content)
    }

    // @@protoc_insertion_point(class_scope:Content)
    private static final Content DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Content();
    }

    public static Content getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Content>
        PARSER = new com.google.protobuf.AbstractParser<Content>() {
      public Content parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Content(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Content> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Content> getParserForType() {
      return PARSER;
    }

    public Content getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Content_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Content_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\016ProtoMsg.proto\";\n\007Content\022\014\n\004uuid\030\001 \001(" +
      "\t\022\021\n\tprotoType\030\003 \001(\r\022\017\n\007content\030\002 \001(\014B\"\n" +
      " com.funstill.netty.chat.protobufb\006proto" +
      "3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Content_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Content_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Content_descriptor,
        new String[] { "Uuid", "ProtoType", "Content", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
