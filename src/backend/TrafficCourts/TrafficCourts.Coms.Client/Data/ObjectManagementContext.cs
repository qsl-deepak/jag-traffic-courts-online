﻿// <auto-generated> This file has been auto generated by EF Core Power Tools. </auto-generated>
#nullable disable
using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using TrafficCourts.Coms.Client.Data.Models;

namespace TrafficCourts.Coms.Client.Data;

public partial class ObjectManagementContext : DbContext
{
    public ObjectManagementContext(DbContextOptions<ObjectManagementContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Models.Metadata> Metadata { get; set; }

    public virtual DbSet<Models.Object> Objects { get; set; }

    public virtual DbSet<Models.Tag> Tags { get; set; }

    public virtual DbSet<Models.Version> Versions { get; set; }

    public virtual DbSet<Models.VersionMetadatum> VersionMetadata { get; set; }

    public virtual DbSet<Models.VersionTag> VersionTags { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Models.Metadata>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("metadata_pkey");

            entity.ToTable("metadata");

            entity.HasIndex(e => e.Key, "metadata_key_index");

            entity.HasIndex(e => new { e.Key, e.Value }, "metadata_key_value_unique").IsUnique();

            entity.HasIndex(e => e.Value, "metadata_value_index");

            entity.Property(e => e.Id)
                .UseIdentityAlwaysColumn()
                .HasColumnName("id");
            entity.Property(e => e.Key)
                .IsRequired()
                .HasMaxLength(255)
                .HasColumnName("key");
            entity.Property(e => e.Value)
                .IsRequired()
                .HasMaxLength(255)
                .HasColumnName("value");
        });

        modelBuilder.Entity<Models.Object>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("object_pkey");

            entity.ToTable("object");

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.Active)
                .IsRequired()
                .HasDefaultValueSql("true")
                .HasColumnName("active");
            entity.Property(e => e.BucketId).HasColumnName("bucketId");
            entity.Property(e => e.CreatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnName("createdAt");
            entity.Property(e => e.CreatedBy)
                .HasMaxLength(255)
                .HasDefaultValueSql("'00000000-0000-0000-0000-000000000000'::character varying")
                .HasColumnName("createdBy");
            entity.Property(e => e.Path)
                .IsRequired()
                .HasMaxLength(1024)
                .HasColumnName("path");
            entity.Property(e => e.Public).HasColumnName("public");
            entity.Property(e => e.UpdatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnName("updatedAt");
            entity.Property(e => e.UpdatedBy)
                .HasMaxLength(255)
                .HasColumnName("updatedBy");
        });

        modelBuilder.Entity<Models.Tag>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("tag_pkey");

            entity.ToTable("tag");

            entity.HasIndex(e => e.Key, "tag_key_index");

            entity.HasIndex(e => new { e.Key, e.Value }, "tag_key_value_unique").IsUnique();

            entity.HasIndex(e => e.Value, "tag_value_index");

            entity.Property(e => e.Id)
                .UseIdentityAlwaysColumn()
                .HasColumnName("id");
            entity.Property(e => e.Key)
                .HasMaxLength(128)
                .HasColumnName("key");
            entity.Property(e => e.Value)
                .HasMaxLength(256)
                .HasColumnName("value");
        });

        modelBuilder.Entity<Models.Version>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("version_pkey");

            entity.ToTable("version");

            entity.HasIndex(e => e.ObjectId, "version_objectid_index");

            entity.HasIndex(e => e.S3VersionId, "version_s3versionid_index");

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.CreatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnName("createdAt");
            entity.Property(e => e.CreatedBy)
                .HasMaxLength(255)
                .HasDefaultValueSql("'00000000-0000-0000-0000-000000000000'::character varying")
                .HasColumnName("createdBy");
            entity.Property(e => e.DeleteMarker).HasColumnName("deleteMarker");
            entity.Property(e => e.MimeType)
                .IsRequired()
                .HasMaxLength(255)
                .HasDefaultValueSql("'application/octet-stream'::character varying")
                .HasColumnName("mimeType");
            entity.Property(e => e.ObjectId).HasColumnName("objectId");
            entity.Property(e => e.S3VersionId)
                .HasMaxLength(1024)
                .HasColumnName("s3VersionId");
            entity.Property(e => e.UpdatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnName("updatedAt");
            entity.Property(e => e.UpdatedBy)
                .HasMaxLength(255)
                .HasColumnName("updatedBy");

            entity.HasOne(d => d.Object).WithMany(p => p.Versions)
                .HasForeignKey(d => d.ObjectId)
                .HasConstraintName("version_objectid_foreign");
        });

        modelBuilder.Entity<Models.VersionMetadatum>(entity =>
        {
            entity.HasKey(e => new { e.VersionId, e.MetadataId }).HasName("version_metadata_pkey");

            entity.ToTable("version_metadata");

            entity.Property(e => e.VersionId).HasColumnName("versionId");
            entity.Property(e => e.MetadataId).HasColumnName("metadataId");
            entity.Property(e => e.CreatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnName("createdAt");
            entity.Property(e => e.CreatedBy)
                .HasMaxLength(255)
                .HasDefaultValueSql("'00000000-0000-0000-0000-000000000000'::character varying")
                .HasColumnName("createdBy");
            entity.Property(e => e.UpdatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnName("updatedAt");
            entity.Property(e => e.UpdatedBy)
                .HasMaxLength(255)
                .HasColumnName("updatedBy");

            entity.HasOne(d => d.Metadata).WithMany(p => p.VersionMetadata)
                .HasForeignKey(d => d.MetadataId)
                .HasConstraintName("version_metadata_metadataid_foreign");

            entity.HasOne(d => d.Version).WithMany(p => p.VersionMetadata)
                .HasForeignKey(d => d.VersionId)
                .HasConstraintName("version_metadata_versionid_foreign");
        });

        modelBuilder.Entity<Models.VersionTag>(entity =>
        {
            entity.HasKey(e => new { e.VersionId, e.TagId }).HasName("version_tag_pkey");

            entity.ToTable("version_tag");

            entity.Property(e => e.VersionId).HasColumnName("versionId");
            entity.Property(e => e.TagId).HasColumnName("tagId");
            entity.Property(e => e.CreatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnName("createdAt");
            entity.Property(e => e.CreatedBy)
                .HasMaxLength(255)
                .HasDefaultValueSql("'00000000-0000-0000-0000-000000000000'::character varying")
                .HasColumnName("createdBy");
            entity.Property(e => e.UpdatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnName("updatedAt");
            entity.Property(e => e.UpdatedBy)
                .HasMaxLength(255)
                .HasColumnName("updatedBy");

            entity.HasOne(d => d.Tag).WithMany(p => p.VersionTags)
                .HasForeignKey(d => d.TagId)
                .HasConstraintName("version_tag_tagid_foreign");

            entity.HasOne(d => d.Version).WithMany(p => p.VersionTags)
                .HasForeignKey(d => d.VersionId)
                .HasConstraintName("version_tag_versionid_foreign");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}