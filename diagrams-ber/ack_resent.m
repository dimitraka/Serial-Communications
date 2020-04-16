function [ noUse ] = barChart( )
  noUse = 0;
  fileId = fopen('ARQresent.txt','r');
  y = fscanf(fileId, '%i');
  fileId = fopen('ARQpackets.txt','r');
  x = fscanf(fileId, '%i');
  x = x';
  y = y';
  
  plot(x,y); 
  set(gca,'FontSize',20);
  title('RESENT ARQ packets','FontSize', 30);
  xlabel('Number of packets','FontSize', 30);
  ylabel({'Response time of resent packets','milliseconds'},'FontSize', 30);
  ylim([0 max(y)]);
  xlim([0 max(x)]);

endfunction
