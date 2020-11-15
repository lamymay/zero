

poi 颜色问题

https://blog.csdn.net/z1074907546/article/details/50544178/


#手动获取颜色
HSSFWorkbook wb=new HSSFWorkbook();
HSSFColor color = wb.getCustomPalette().findColor(byte red,
                           byte green,
                           byte blue)


red - the RGB red component, between 0 and 255 inclusive
green - the RGB green component, between 0 and 255 inclusive
blue - the RGB blue component, between 0 and 255 inclusive
