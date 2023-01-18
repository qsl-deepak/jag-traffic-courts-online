﻿using Moq;

namespace TrafficCourts.Coms.Client.Test.ServiceTests;

public class GetFileAsync : ObjectManagementServiceTest
{
    [Fact]
    public async Task should_copy_data_when_file_is_found()
    {
        Guid id = Guid.NewGuid();

        // create some sample data data
        Dictionary<string, IEnumerable<string>> headers = new()
        {
            { "x-amz-meta-id", new string[] { id.ToString("d") } },
            { "x-amz-meta-name", new string[] { "filename.png" } },
            { "x-amz-meta-type", new string[] { "picture" } }
        };

        var stream = GetRandomStream();
        SetupReadObjectReturn(new FileResponse(200, headers, stream, null, null));

        CancellationTokenSource cts = new CancellationTokenSource();

        ObjectManagementService sut = GetService();

        // act
        File actualFile = await sut.GetFileAsync(id, false, cts.Token);

        // assert
        // should have read the object to get the expected found object
        _mockClient.Verify(_ => _.ReadObjectAsync(
             It.Is<Guid>((actual) => actual == id),
             It.Is<DownloadMode?>((actual) => actual == DownloadMode.Proxy),
             It.Is<int?>((actual) => actual == null),
             It.Is<string?>((actual) => actual == null),
            It.Is<CancellationToken>((actual) => actual == cts.Token)
        ));

        Assert.NotNull(actualFile);
        Assert.NotNull(actualFile.Data);
        Assert.NotEqual(stream, actualFile.Data); // should NOT return the same stream
        Assert.Equal("filename.png", actualFile.FileName);

        // should be only 1 meta data field
        Assert.Single(actualFile.Metadata);
        Assert.Equal("picture", actualFile.Metadata["type"]);

        var expected = Assert.IsAssignableFrom<MemoryStream>(stream).ToArray();
        var actual = Assert.IsAssignableFrom<MemoryStream>(actualFile.Data).ToArray();

        // ensure all the data matches
        Assert.Equal(expected.Length, actual.Length); // data size should be same
        for (int i = 0; i < expected.Length; i++)
        {
            Assert.Equal(expected[i], actual[i]);
        }
    }
}