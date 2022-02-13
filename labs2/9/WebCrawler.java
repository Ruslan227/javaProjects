package info.kgeorgiy.ja.aliev.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.*;

public class WebCrawler implements Crawler {

    private final ExecutorService downloaderPool, extractorPool;
    private final Downloader downloader;

    /**
     * @param downloader  attempts download web-page and get link (throws IOException)
     * @param downloaders максимальное число одновременно загружаемых страниц
     * @param extractors  максимальное число страниц, из которых одновременно извлекаются ссылки
     * @param perHost     максимальное число страниц, одновременно загружаемых c одного хоста.
     *                    Для определения хоста следует использовать метод getHost класса URLUtils из тестов
     */
    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        downloaderPool = Executors.newFixedThreadPool(downloaders);
        extractorPool = Executors.newFixedThreadPool(extractors);
        this.downloader = downloader;
    }


    /**
     * @param url   start <a href="http://tools.ietf.org/html/rfc3986">URL</a>.
     * @param depth download depth.
     * @return Result - map <String SitesAndFilesName, IOException errors>
     */
    @Override
    public Result download(String url, int depth) {
        ConcurrentMap<String, IOException> exceptions = new ConcurrentHashMap<>();
        Set<String> downloadedPages = ConcurrentHashMap.newKeySet();
        Set<String> visitedLinks = ConcurrentHashMap.newKeySet();
        visitedLinks.add(url);
        Phaser phaser = new Phaser(1);
        recursiveDownload(depth, url, phaser, downloadedPages, exceptions, visitedLinks);
        phaser.arriveAndAwaitAdvance();

        return new Result(new ArrayList<>(downloadedPages), exceptions);
    }

    private void recursiveDownload(int depth,
                                   String url,
                                   Phaser phaser,
                                   Set<String> downloadedPages,
                                   ConcurrentMap<String, IOException> exceptions,
                                   Set<String> visitedLinks) {
        try {
            String ignoredHost = URLUtils.getHost(url);
            phaser.register();

            Runnable downloaderTask = () -> {
                try {
                    Document downloaded = downloader.download(url);
                    downloadedPages.add(url);

                    if (depth != 1) {

                        phaser.register();

                        Runnable extractorTask = () -> {
                            try {
                                for (String link : downloaded.extractLinks()) {
                                    if (visitedLinks.add(link)) {
                                        recursiveDownload(depth - 1, link, phaser, downloadedPages, exceptions, visitedLinks);
                                    }
                                }
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            } finally {
                                phaser.arrive();
                            }
                        };

                        extractorPool.submit(extractorTask);
                    }
                } catch (IOException e) {
                    exceptions.put(url, e);
                } finally {
                    phaser.arrive();
                }
            };
            downloaderPool.submit(downloaderTask);
        } catch (MalformedURLException e) {
            exceptions.put(url, e);
        }

    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(10, TimeUnit.SECONDS))
                    shutdownAndAwaitTermination(pool);
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
        }
    }

    @Override
    public void close() {
        shutdownAndAwaitTermination(downloaderPool);
        shutdownAndAwaitTermination(extractorPool);
    }

    private static int getArg(String[] args, int defaultVal, int ind) {
        if (args.length <= ind) {
            return defaultVal;
        } else {
            return Integer.parseInt(args[ind]);
        }
    }

    public static void main(String[] args) {

        if (args == null || args.length > 5 || args.length < 1) {
            System.err.println("WebCrawler url [depth [downloaders [extractors [perHost]]]]");
            return;
        }

        String url = args[0];
        int depth = getArg(args, 2, 1),
                downloaders = getArg(args, 8, 2),
                extractors = getArg(args, 8, 3),
                perHost = getArg(args, 8, 4);

        try {
            Downloader downloader = new CachingDownloader();
            WebCrawler webCrawler = new WebCrawler(downloader, downloaders, extractors, perHost);
            webCrawler.download(url, depth);
        } catch (IOException e) {
            System.out.println("Problems while creating CachingDownloader: " + e.getMessage());
        }

    }
}


