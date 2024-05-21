public class Stock
{
  private String symbol;
  private double price;
  private long volume;
  private long marketCap;

  public Stock (String symbol, double price, long volume, long marketCap)
  {
    this.symbol = symbol;
    this.price = price;
    this.volume = volume;
    this.marketCap = marketCap;
  }

  public String
  getSymbol ()
  {
    return symbol;
  }

  public void
  setSymbol (String symbol)
  {
    this.symbol = symbol;
  }

  public double
  getPrice ()
  {
    return price;
  }

  public void
  setPrice (double price)
  {
    this.price = price;
  }

  public long
  getVolume ()
  {
    return volume;
  }

  public void
  setVolume (long volume)
  {
    this.volume = volume;
  }

  public long
  getMarketCap ()
  {
    return marketCap;
  }

  public void
  setMarketCap (long marketCap)
  {
    this.marketCap = marketCap;
  }

  @Override
  public String
  toString ()
  {
    String formatted = String.format (
        "Stock [ symbol = %10s , price = %10.2f , volume = %20d , marketCap = %20d ]",
        symbol, price, volume, marketCap);
    return formatted;
  }
}
