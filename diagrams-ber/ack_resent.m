function [ noUse ] = barChart( )
  noUse = 0;
  fileId = fopen('ARQresent.txt','r');
  y = fscanf(fileId, '%i');
  fileId = fopen('ARQpackets.txt','r');
  x = fscanf(fileId, '%i');
  x = x';
  y = y';
  
  plot(x,y);
  title('RESENT ARQ packets','FontSize', 30);
  xlabel('Number of packets','FontSize', 20);
  ylabel({'Response time of resent packets','milliseconds'},'FontSize', 20);
  ylim([0 max(y)]);
  xlim([0 max(x)]);

endfunction
